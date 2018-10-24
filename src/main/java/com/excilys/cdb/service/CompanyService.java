package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.PageNew;

public class CompanyService {

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

	public <T> List<Company> findAll() throws SQLException, PremierePageException, DernierePageException {
//		return Page.getPage(daoCompany.findAll(), choix, 10);
		return PageNew.pagination(daoCompany.findAll(), 1, 10);
	}

}