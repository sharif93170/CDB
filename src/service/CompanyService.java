package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoCompany;
import model.Company;

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

	public ArrayList<Company> getListCompanies() throws SQLException {
		return dc.getListCompanies();
	}

}
