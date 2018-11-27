package com.excilys.cdb.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exceptions.CdbException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerDTOValidator;

@Controller
@RequestMapping("/computer")
public class ComputerController {

	private int nombrElementPerPage = 10;
	private String search = "";
	private int pageIndex = 0;
	private List<Computer> computers;
	private List<CompanyDTO> companies;
	private Page<Computer> computerPage;
	private ComputerService computerService;
	private CompanyService companyService;
	private CompanyMapper companyMapper = new CompanyMapper();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);
	final private String DASHBOARD = "dashboard";
	final private String ADD_COMPUTER = "addComputer";
	final private String EDIT_COMPUTER = "editComputer";

	@InitBinder("ComputerDTO")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}

	private ComputerController(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String IndexComputer(Locale locale, ModelMap model) {
		pageIndex = 0;
		computerPage = computerService.findByComputerAndCompanyNameLimit(search, pageIndex, nombrElementPerPage);
		computers = computerPage.getContent();
		defaultElementToSendToDashboard(model);
		return DASHBOARD;
	}

	@RequestMapping(value = "/searchPage", method = RequestMethod.GET)
	public String changePage(@RequestParam(value = "pageNumber") int pageNumber, ModelMap model) {

		pageIndex = pageNumber;
		computerPage = computerService.findByComputerAndCompanyNameLimit(search, pageIndex, nombrElementPerPage);
		computers = computerPage.getContent();

		defaultElementToSendToDashboard(model);
		return DASHBOARD;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(value = "search") String pSearch, ModelMap model) {

		search = pSearch;
		computerPage = computerService.findByComputerAndCompanyNameLimit(search, pageIndex, nombrElementPerPage);
		computers = computerPage.getContent();

		defaultElementToSendToDashboard(model);
		return DASHBOARD;
	}

	@RequestMapping(value = "/nombreElementPerPage", method = RequestMethod.GET)
	public String changeNumberOfElementDisplayPerPage(
			@RequestParam(value = "buttonSetNumberElementDisplayed") int numberElementDisplayed, ModelMap model) {

		nombrElementPerPage = numberElementDisplayed;
		computerPage = computerService.findByComputerAndCompanyNameLimit(search, pageIndex, nombrElementPerPage);
		computers = computerPage.getContent();

		defaultElementToSendToDashboard(model);
		return DASHBOARD;
	}

	@RequestMapping(value = "/editComputer", method = RequestMethod.GET)
	public String editComputerGetPage(@RequestParam(value = "ComputerToModifie") long idComputerToModifie,
			ModelMap model) {

		companies = companyMapper.mapCompanyToCompanyDTO(companyService.findAll());

		if (idComputerToModifie != 0l) {
			long computerId = idComputerToModifie;
			model.addAttribute("ComputerDTO", new ComputerDTO());
			Computer computer = computerService.findById(computerId);
			model.addAttribute("computer", computer);
		}

		model.addAttribute("companies", companies);
		return EDIT_COMPUTER;
	}

	@RequestMapping(value = "/editComputer", method = RequestMethod.POST)
	public String editComputer(@ModelAttribute("ComputerDTO") @Validated(ComputerDTO.class) ComputerDTO computerDTO,
			BindingResult result, ModelMap model) {

		companies = companyMapper.mapCompanyToCompanyDTO(companyService.findAll());
		model.addAttribute("companies", companies);
		if (result.hasErrors()) {
			return EDIT_COMPUTER;
		}
		String errors = "";
		boolean success = false;

		Computer computer = computerService.findById(computerDTO.getId());

		ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDTO.getName());

		if (!StringUtils.isBlank(computerDTO.getIntroduced())) {
			computerBuilder.introduceDate(LocalDate.parse(computerDTO.getIntroduced(), formatter));
		} else {
			computerBuilder.introduceDate(null);
		}
		if (!StringUtils.isBlank(computerDTO.getDiscontinued())) {
			computerBuilder.discontinuedDate(LocalDate.parse(computerDTO.getDiscontinued(), formatter));
		} else {
			computerBuilder.discontinuedDate(null);
		}

		if (computerDTO.getCompanyId() > 0) {
			computerBuilder.company(companyService.findById(computerDTO.getCompanyId()));
		}

		computer = computerBuilder.build();

		try {

			if (computerService.update(computer).getId() == computer.getId()) {
				success = true;
			} else {
				success = false;
			}
		} catch (CdbException e) {
			errors += e.getMessage();
		}

		model.addAttribute("errors", errors);
		model.addAttribute("success", success);
		model.addAttribute("computer", computer);
		return EDIT_COMPUTER;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public String addComputerGetPage(ModelMap model) {
		companies = companyMapper.mapCompanyToCompanyDTO(companyService.findAll());
		model.addAttribute("ComputerDTO", new ComputerDTO());
		model.addAttribute("companies", companies);
		return ADD_COMPUTER;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public String addComputer(@ModelAttribute("ComputerDTO") @Validated(ComputerDTO.class) ComputerDTO computerDTO,
			BindingResult result, Locale locale, ModelMap model) {

		companies = companyMapper.mapCompanyToCompanyDTO(companyService.findAll());
		model.addAttribute("companies", companies);
		if (result.hasErrors()) {
			return ADD_COMPUTER;
		}
		String errors = "";
		boolean success = false;
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDTO.getName());

		if (!StringUtils.isBlank(computerDTO.getIntroduced())) {
			computerBuilder.introduceDate(LocalDate.parse(computerDTO.getIntroduced(), formatter));
		}
		if (!StringUtils.isBlank(computerDTO.getDiscontinued())) {
			computerBuilder.discontinuedDate(LocalDate.parse(computerDTO.getDiscontinued(), formatter));
		}

		if (computerDTO.getCompanyId() > 0) {
			computerBuilder.company(companyService.findById(computerDTO.getCompanyId()));
		}

		Computer computer = computerBuilder.build();

		try {
			if (computerService.save(computer).getId() == computer.getId()) {
				success = true;
			} else {
				success = false;
			}
		} catch (CdbException e) {
			errors += e.getMessage();
		}

		model.addAttribute("errors", errors);
		model.addAttribute("success", success);
		model.addAttribute("computer", computer);
		return ADD_COMPUTER;
	}

	@RequestMapping(value = "/deleteComputer", method = RequestMethod.POST)
	public String deleteComputer(@RequestParam(value = "selection") String idComputerSend, ModelMap model) {
		String deleteState = "";
		boolean computerDeleteSuccess = false;
		List<String> listIdComputer = new ArrayList<String>(Arrays.asList(idComputerSend.split(",")));
		if (listIdComputer.size() > 1) {
			computerDeleteSuccess = true;
		}
		for (int i = 0; i < listIdComputer.size(); i++) {
			if (computerService.delete(Integer.parseInt(listIdComputer.get(i))) != 0) {
				deleteState += listIdComputer.get(i) + " , ";
			} else {
				deleteState += "error, cannot delete " + listIdComputer.get(i) + " ";
			}
		}

		computerPage = computerService.findByComputerAndCompanyNameLimit(search, pageIndex, nombrElementPerPage);
		computers = computerPage.getContent();

		model.addAttribute("computerDeleteSuccess", computerDeleteSuccess);
		model.addAttribute("deleteState", deleteState);
		defaultElementToSendToDashboard(model);
		return DASHBOARD;
	}

	private void defaultElementToSendToDashboard(ModelMap model) {
		model.addAttribute("search", search);
		model.addAttribute("numberElementPerPage", nombrElementPerPage);
		model.addAttribute("numberOfComputers", computerPage.getTotalElements());
		model.addAttribute("computers", computers);
		model.addAttribute("computerPage", computerPage.getNumber());
		model.addAttribute("numberTotalOfPages", computerPage.getTotalPages() - 1);
	}

}