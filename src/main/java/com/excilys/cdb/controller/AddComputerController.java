package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class AddComputerController {

	Logger logger = LoggerFactory.getLogger(AddComputerController.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;

	private CompanyDtoMapper companyMapper = CompanyDtoMapper.getInstance();
	private ComputerDtoMapper computerMapper = ComputerDtoMapper.getInstance();

	@GetMapping("addComputer")
	public String getDashboard(ModelMap model)
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> subCompaniesDTO = new ArrayList<CompanyDTO>();
		for (int i = 0; i < companies.size(); i++) {
			subCompaniesDTO.add(companyMapper.toCompanyDTO(companies.get(i)));
		}
		model.addAttribute("companies", companies);
		return "addComputer";
	}

	@PostMapping("addComputer")
	public String postDeleteComputer(ModelMap model, @RequestParam String computerName, @RequestParam String introduced,
			@RequestParam String discontinued, @RequestParam String companyId)
			throws IOException, SQLException, DBException {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setName(computerName);
		computerDto.setIntroduced(introduced);
		computerDto.setDiscontinued(discontinued);
		computerDto.setCompanyId(companyId);

		try {
			computerService.create(computerMapper.fromComputerDTO(computerDto));
			return "redirect:dashboard";
		} catch (DataException de) {
			model.addAttribute("internError", de.getMessage());
			return "addComputer";
		}
	}

}
