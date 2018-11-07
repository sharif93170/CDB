package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;

public class DaoCompany {

	private static final Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	private static String SELECT_ALL_SQL = "SELECT id, name FROM company";

	private static DaoCompany daoCompany = new DaoCompany();
	private static Connection connect;

	private DaoCompany() {
		ConnexionMySQL.getInstance();
		connect = ConnexionMySQL.getConnection();
	}

	public static DaoCompany getInstance() {
		return daoCompany;
	}

	public ArrayList<Company> findAll() throws SQLException, IOException {

		DaoCompany.connect = ConnexionMySQL.connect();

		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			ArrayList<Company> listCompanies = new ArrayList<>();
			while (rs.next()) {
				listCompanies.add(new Company.CompanyBuilder(rs.getLong("id")).name(rs.getString("name")).build());
			}

			return listCompanies;

		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
			return null;
		} finally {
			DaoCompany.connect.close();
		}

	}

}