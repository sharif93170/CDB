package ui;

import java.sql.SQLException;

import dao.DaoCompany;
import dao.DaoComputer;
import jdbc.ConnexionMySQL;

public class MainTest {

	public static void main(String[] args) throws SQLException {

//		ConnexionMySQL.getInstance();

		DaoComputer.getInstance().getListComputers();

//		DaoCompany.getInstance().getListCompanies();

	}

}
