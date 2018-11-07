package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;

public class CompanyService {

	static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private static CompanyService companyService = null;
	DaoCompany daoCompany;

	private CompanyService() {
		daoCompany = DaoCompany.getInstance();
	}

	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	public <T> List<Company> findAll() throws SQLException, PremierePageException, DernierePageException, IOException {
		return daoCompany.findAll();
	}

}