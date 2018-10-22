package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;

public class DaoCompany {

	private static String SELECT_ALL_SQL = "SELECT id, name FROM company";

	static DaoCompany daoCompany = new DaoCompany();
	Connection conn;

	private DaoCompany() {
		conn = ConnexionMySQL.getInstance();
	}

	public static DaoCompany getInstance() {
		return daoCompany;
	}

	public ArrayList<Company> findAll() throws SQLException {

		try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			ArrayList<Company> listCompanies = new ArrayList<>();
			while (rs.next()) {
				listCompanies.add(new Company(rs.getLong("id"), rs.getString("name")));
			}

			System.out.println("Success !");
			return listCompanies;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}

	}

}