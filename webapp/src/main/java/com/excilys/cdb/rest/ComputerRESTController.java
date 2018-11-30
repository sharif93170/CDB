package com.excilys.cdb.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.ComputerService;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@RestController("computerRESTController")
@RequestMapping("/api/computer")
public class ComputerRESTController {

	Logger logger = LoggerFactory.getLogger(ComputerRESTController.class);

	private final ComputerService computerService;
	private final ComputerDtoMapper computerMapper;

	public ComputerRESTController(ComputerService computerService, ComputerDtoMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@GetMapping({ "/count", "/count/{name}" })
	public ResponseEntity<Integer> count(@PathVariable("name") Optional<String> name) {
		int count;
		if (name.isPresent()) {
			count = computerService.count(name.get());
		} else {
			count = computerService.count("");
		}
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> find(@PathVariable("id") int id) {
		ComputerDTO computerDto = computerMapper.toComputerDTO(computerService.showDetails(id));
		return new ResponseEntity<>(computerDto, HttpStatus.OK);
	}

	@GetMapping({ "/all", "/all/{name}" })
	public ResponseEntity<List<ComputerDTO>> findAll(@PathVariable("name") Optional<String> name,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDTO> computerDTOList = new ArrayList<>();
		try {
			Page.setPage(page, size);
			if (name.isPresent()) {
				computerList = computerService.findAll(name.get());
			} else {
				computerList = computerService.findAll("");
			}

			computerDTOList = computerList.stream().map(computerMapper::toComputerDTO).collect(Collectors.toList());
		} catch (PremierePageException ppe) {
			Page.pagePlus();
		} catch (DernierePageException dpe) {
			Page.pageMinus();
		}
		if (computerList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(computerDTOList, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<ComputerDTO> create(@RequestBody ComputerDTO computerDto) {
		try {
			computerService.create(computerMapper.fromComputerDTO(computerDto));
		} catch (DataException de) {
			logger.error(de.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<ComputerDTO> update(@RequestBody ComputerDTO computerDto) {
		try {
			computerService.update(computerMapper.fromComputerDTO(computerDto));
		} catch (DataException de) {
			logger.error(de.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<Void> deleteSelection(@RequestParam String[] idTab) {
		computerService.deleteSelection(idTab);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
