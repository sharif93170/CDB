package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import jdbc.ConnexionMySQL;
import model.Company;

public class DaoCompany {

	static DaoCompany daoCompany = new DaoCompany();
	Connection conn;

	private DaoCompany() {
		conn = ConnexionMySQL.getInstance();
	}

	public static DaoCompany getInstance() {
		return daoCompany;
	}

	public ArrayList<Company> listCompanies() throws SQLException {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM company");
			rs = preparedStatement.executeQuery();
			ArrayList<Company> listCompanies = new ArrayList<>();
			while (rs.next()) {
				listCompanies.add(new Company(rs.getInt(1), rs.getString(2)));
			}

			System.out.println("Success !");
			return listCompanies;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		} finally {
			rs.close();
			preparedStatement.close();
		}

	}

}
