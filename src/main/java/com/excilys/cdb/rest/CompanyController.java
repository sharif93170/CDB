package com.excilys.cdb.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;

@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Company>> findAll()
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		return new ResponseEntity<List<Company>>(companyService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(int id) throws IOException, DBException, SQLException {
		companyService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

}
