package com.excilys.cdb.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("addComputer")
public class AddComputerController {

	Logger logger = LoggerFactory.getLogger(AddComputerController.class);

	private final CompanyService companyService;
	private final ComputerService computerService;
	private final CompanyDtoMapper companyMapper;
	private final ComputerDtoMapper computerMapper;

	public AddComputerController(CompanyService companyService, ComputerService computerService,
			CompanyDtoMapper companyMapper, ComputerDtoMapper computerMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
	}

	@GetMapping
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

	@PostMapping
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

}
