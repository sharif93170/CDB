package com.excilys.cdb.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.ComputerService;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Controller("computerController")
@RequestMapping("/computer")
public class ComputerController {

	Logger logger = LoggerFactory.getLogger(ComputerController.class);

	private final ComputerService computerService;
	private final ComputerDtoMapper computerMapper;

	public ComputerController(ComputerService computerService, ComputerDtoMapper computerMapper) {
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

	@GetMapping()
	public ResponseEntity<List<Computer>> findAll()
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		return new ResponseEntity<List<Computer>>(computerService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Computer>> findAll(String name)
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		return new ResponseEntity<List<Computer>>(computerService.findAll(name), HttpStatus.OK);
	}

	@GetMapping({ "/all", "/all/{name}" })
	public ResponseEntity<List<ComputerDTO>> findAll(@PathVariable("name") Optional<String> name,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDTO> subComputersDTO = new ArrayList<>();
		try {
			Page.setPage(page, size);
			if (name.isPresent()) {
				computerList = computerService.findAll(name.get());
			} else {
				computerList = computerService.findAll("");
			}

			subComputersDTO = computerList.stream().map(computerMapper::toComputerDTO).collect(Collectors.toList());
		} catch (PremierePageException ppe) {
			Page.pagePlus();
		} catch (DernierePageException dpe) {
			Page.pageMinus();
		}
		if (computerList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(subComputersDTO, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<Void> create(Computer computer) throws IOException, SQLException, DBException, DataException {
		computerService.create(computer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> update(Computer computer) throws IOException, SQLException, DBException, DataException {
		computerService.update(computer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteByName(String nameToDelete) throws IOException, SQLException, DBException {
		computerService.deleteByName(nameToDelete);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteById(int idToDelete) throws IOException, SQLException, DBException {
		computerService.deleteById(idToDelete);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteSelection(String[] idTab) throws IOException, SQLException, DBException {
		computerService.deleteSelection(idTab);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

}
