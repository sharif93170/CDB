package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Controller
public class DashboardController {

	Logger logger = LoggerFactory.getLogger(DashboardController.class);

	private final CompanyService companyService;
	private final ComputerService computerService;
	private final CompanyDtoMapper companyMapper;
	private final ComputerDtoMapper computerMapper;

	public DashboardController(CompanyService companyService, ComputerService computerService,
			CompanyDtoMapper companyMapper, ComputerDtoMapper computerMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
	}

	@GetMapping("dashboard")
	public String getDashboard(ModelMap model, @RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computers;
		List<ComputerDTO> computerPageDTO = new ArrayList<ComputerDTO>();
		int computerTotal = 0;
		try {
			Page.setPage(page, size);

			if (search == null) {
				computers = computerService.findAll("");
				computerTotal = computerService.count("");
			} else {
				model.addAttribute("search", search);
				computers = computerService.findAll(search);
				computerTotal = computerService.count(search);
			}
			computerPageDTO.clear();
			for (int i = 0; i < computers.size(); i++) {
				computerPageDTO.add(computerMapper.toComputerDTO(computers.get(i)));
			}
		} catch (PremierePageException ppe) {
			Page.pagePlus();
		} catch (DernierePageException dpe) {
			Page.pageMinus();
		}
		model.addAttribute("computerTotal", computerTotal);
		model.addAttribute("pageActual", Page.getPage());
		model.addAttribute("pageSize", Page.getPageSize());
		model.addAttribute("computerPage", computerPageDTO);
		return "dashboard";
	}

	@PostMapping("dashboard")
	public String postDeleteComputer(ModelMap model, @RequestParam String[] selection) {
		String[] idTab = selection[0].split(",");
		computerService.deleteSelection(idTab);

		return "redirect:dashboard";
	}

	@GetMapping("addComputer")
	public String getAddComputer(ModelMap model) {
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = companies.stream().map(tmp -> {
			CompanyDTO cpnDto = companyMapper.toCompanyDTO(tmp);
			return cpnDto;
		}).collect(Collectors.toList());
		model.addAttribute("companies", companiesDTO);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "addComputer";
	}

	@PostMapping("addComputer")
	public String postAddComputer(ModelMap model, @Validated @ModelAttribute("computerDTO") ComputerDTO computerDto,
			BindingResult result) {

		if (result.hasErrors()) {
			return "500";
		}

		try {
			computerService.create(computerMapper.fromComputerDTO(computerDto));
			return "redirect:dashboard";
		} catch (DataException de) {
			model.addAttribute("internError", de.getMessage());
			logger.error(de.getMessage());
			return "addComputer";
		}
	}

	@GetMapping("editComputer")
	public String getEditComputer(ModelMap model, @RequestParam String id) {
		ComputerDTO computerDto = computerMapper.toComputerDTO(computerService.showDetails(Integer.parseInt(id)));
		model.addAttribute("computerDto", computerDto);

		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = companies.stream().map(tmp -> {
			CompanyDTO cpnDto = companyMapper.toCompanyDTO(tmp);
			return cpnDto;
		}).collect(Collectors.toList());
		model.addAttribute("companies", companiesDTO);

		return "editComputer";
	}

	@PostMapping("editComputer")
	public String postEditComputer(ModelMap model, @Validated @ModelAttribute("computerDTO") ComputerDTO computerDto,
			BindingResult result) {

		if (result.hasErrors()) {
			return "editComputer";
		}

		try {

			computerService.update(computerMapper.fromComputerDTO(computerDto));

			return "redirect:dashboard";
		} catch (DataException de) {
			model.addAttribute("internError", de.getMessage());
			logger.error(de.getMessage());
			return "editComputer";
		}
	}

}
