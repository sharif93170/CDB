package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.service.ComputerService;

import com.excilys.cdb.model.Computer;

@Controller("companyController")
@RequestMapping("/company")
public class ComputerController {

	private final ComputerService computerService;

	public ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}

	@GetMapping()
	public ResponseEntity<Integer> count(String name) throws IOException, DBException, SQLException {
		return new ResponseEntity<Integer>(computerService.count(name), HttpStatus.OK);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Computer> showDetails(int idComputer) throws IOException, DBException, SQLException {
		return new ResponseEntity<Computer>(computerService.showDetails(idComputer), HttpStatus.OK);
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

	@PostMapping
	public ResponseEntity<Void> create(Computer computer) throws IOException, SQLException, DBException, DataException {
		computerService.create(computer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Void> update(int idComputer, Computer computer)
			throws IOException, SQLException, DBException, DataException {
		computerService.update(idComputer, computer);
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
