package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;

@Repository
public class DaoCompany {

	private static final Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	private final static String SELECT_ALL_SQL = "SELECT id, name FROM company";
	private final static String DELETE_COMPANY_BY_ID_SQL = "DELETE FROM company WHERE id= ?";

	private static DaoCompany daoCompany = new DaoCompany();
	private static Connection connect;

	private DaoCompany() {
		ConnexionMySQL.getInstance();
		connect = ConnexionMySQL.getConnection();
	}

	public static DaoCompany getInstance() {
		return daoCompany;
	}

	public ArrayList<Company> findAll() throws SQLException, IOException, DBException {

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
			try {
				DaoCompany.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}

	}

	public void delete(int idCompany) throws IOException, DBException {

		DaoCompany.connect = ConnexionMySQL.connect();

		try (PreparedStatement preparedStatement = DaoCompany.connect.prepareStatement(DELETE_COMPANY_BY_ID_SQL)) {
			preparedStatement.setInt(1, idCompany);
			preparedStatement.executeUpdate();

		} catch (SQLException sql) {
			logger.error(sql.getMessage());
		} finally {
			try {
				DaoCompany.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

}