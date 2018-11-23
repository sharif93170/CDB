package com.excilys.cdb.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyRepository;
import com.excilys.cdb.dao.ComputerRepository;
import com.excilys.cdb.model.Company;

@Service
@EnableJpaRepositories(basePackages = "com.excilys.cdb.dao")
@Scope("singleton")
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ComputerRepository computerRepository;

	public ArrayList<Company> getCompanies() {
		return (ArrayList<Company>) companyRepository.findAll();
	}

	public boolean checkIdCompany(long id) {
		return companyRepository.findById(id).isPresent();
	}

	@Transactional(rollbackOn = Exception.class)
	public boolean deleteCompanyById(long id) {
		computerRepository.deleteComputersByCompanyId(id);
		companyRepository.deleteById(id);
		return true;
	}

}