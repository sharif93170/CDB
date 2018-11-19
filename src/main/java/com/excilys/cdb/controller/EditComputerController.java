package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("editComputer")
public class EditComputerController {

	Logger logger = LoggerFactory.getLogger(EditComputerController.class);

	private final CompanyService companyService;
	private final ComputerService computerService;
	private final CompanyDtoMapper companyMapper;
	private final ComputerDtoMapper computerMapper;

	public EditComputerController(CompanyService companyService, ComputerService computerService,
			CompanyDtoMapper companyMapper, ComputerDtoMapper computerMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
	}

	@GetMapping
	public String getEditComputer(ModelMap model, @RequestParam String id) {
		ComputerDTO computerDto = computerMapper.toComputerDTO(computerService.showDetails(Integer.parseInt(id)));
		model.addAttribute("computerDto", computerDto);

		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (int i = 0; i < companies.size(); i++) {
			companiesDTO.add(companyMapper.toCompanyDTO(companies.get(i)));
		}
		model.addAttribute("companies", companiesDTO);

		return "editComputer";
	}

	@PostMapping
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
