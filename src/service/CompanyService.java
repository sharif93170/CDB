package service;

import java.sql.SQLException;
import java.util.List;

import dao.DaoCompany;
import model.Company;
import ui.Page;

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

	public <T> List<Company> listCompanies(int choix) throws SQLException {
		return Page.getPage(dc.listCompanies(), choix, 10);
	}

}
