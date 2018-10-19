package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyService {

	private static CompanyService cs = null;
	DaoCompany dc;

	private CompanyService() {
		dc = DaoCompany.getInstance();
	}

	public static CompanyService getInstance() {
		if (cs == null) {
			cs = new CompanyService();
		}
		return cs;
	}

	public <T> List<Company> findAll(int choix) throws SQLException {
		return Page.getPage(dc.findAll(), choix, 10);
	}

}
