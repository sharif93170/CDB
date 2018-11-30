package com.excilys.cdb.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.model.Company;

@RestController("companyRESTController")
@RequestMapping("/api/company")
public class CompanyRESTController {

	Logger logger = LoggerFactory.getLogger(CompanyRESTController.class);

	private final CompanyService companyService;
	private final CompanyDtoMapper companyMapper;

	public CompanyRESTController(CompanyService companyService, CompanyDtoMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	@GetMapping("/all")
	public ResponseEntity<List<CompanyDTO>> findAll() {
		List<Company> companyList;
		companyList = companyService.findAll();
		List<CompanyDTO> companiesDTOList = companyList.stream().map(companyMapper::toCompanyDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(companiesDTOList, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		companyService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
