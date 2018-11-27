package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.ComputerService;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Controller
public class DashboardController {

	@Autowired
	private ComputerService computerService;
	private ComputerDtoMapper computerMapper;

	public DashboardController(ComputerService computerService, ComputerDtoMapper computerMapper) {
		this.computerService = computerService;
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

}
