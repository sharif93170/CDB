package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DaoComputer {

	static Logger logger = LoggerFactory.getLogger(DaoComputer.class);

	private final static String COUNT_SQL = "SELECT COUNT(computer.id) FROM computer AS computer LEFT JOIN company AS company ON computer.company_id = company.id WHERE UPPER(computer.name) LIKE UPPER(?) OR UPPER(company.name) LIKE UPPER(?) ";
	private final static String SELECT_DETAILS_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE computer.id = ?";
	private final static String SELECT_ALL_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final static String SELECT_BY_NAME_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE UPPER(computer.name) LIKE UPPER(?) OR UPPER(company.name) LIKE UPPER(?) ORDER BY computer.name LIMIT ? OFFSET ?";
	private final static String INSERT_SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private final static String UPDATE_SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final static String DELETE_BY_ID_SQL = "DELETE FROM computer WHERE id = ?";
	private final static String DELETE_BY_COMPANY_ID_SQL = "DELETE FROM computer WHERE company_id = ?";
	private final static String DELETE_BY_NAME_SQL = "DELETE FROM computer WHERE name = ?";

	private static DaoComputer daoComputer = new DaoComputer();
	private static Connection connect;

	private DaoComputer() {
		ConnexionMySQL.getInstance();
		connect = ConnexionMySQL.getConnection();
	}

	public static DaoComputer getInstance() {
		return daoComputer;
	}

	public int count(String name) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		int count = 0;
		try (PreparedStatement preparedStatement = connect.prepareStatement(COUNT_SQL)) {
			preparedStatement.setNString(1, "%" + name + "%");
			preparedStatement.setNString(2, "%" + name + "%");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}
			result.close();
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
		return count;
	}

	public Computer showDetails(int idComputer) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		ResultSet rs = null;
		Computer computer = null;
		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_DETAILS_SQL)) {
			preparedStatement.setInt(1, idComputer);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				LocalDate introduced;
				LocalDate discontinued;
				if (rs.getDate("computer.introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("computer.introduced").toLocalDate();
				}
				if (rs.getDate("computer.discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("computer.discontinued").toLocalDate();
				}

				computer = new Computer.ComputerBuilder(rs.getString("computer.name")).id(rs.getLong("computer.id"))
						.introduceDate(introduced).discontinuedDate(discontinued)
						.company(new Company.CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
								.build())
						.build();

			}
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
		return computer;
	}

	public ArrayList<Computer> findByName(String name, int page, int size)
			throws SQLException, PremierePageException, IOException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		ResultSet rs = null;
		ArrayList<Computer> listComputers = new ArrayList<>();
		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_BY_NAME_SQL)) {
			preparedStatement.setString(1, "%" + name + "%");
			preparedStatement.setString(2, "%" + name + "%");
			preparedStatement.setInt(3, size);
			preparedStatement.setInt(4, (page - 1) * size);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {

				LocalDate introduced, discontinued;

				if (rs.getDate("computer.introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("computer.introduced").toLocalDate();
				}
				if (rs.getDate("computer.discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("computer.discontinued").toLocalDate();
				}

				listComputers.add(new Computer.ComputerBuilder(rs.getString("computer.name"))
						.id(rs.getLong("computer.id")).introduceDate(introduced).discontinuedDate(discontinued)
						.company(new Company.CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
								.build())
						.build());

			}
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
		return listComputers;
	}

	public ArrayList<Computer> findAll(int page, int size)
			throws SQLException, PremierePageException, IOException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		ResultSet rs = null;
		ArrayList<Computer> listComputers = new ArrayList<>();
		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_ALL_SQL)) {
			preparedStatement.setInt(1, size);
			preparedStatement.setInt(2, (page - 1) * size);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {

				LocalDate introduced, discontinued;

				if (rs.getDate("computer.introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("computer.introduced").toLocalDate();
				}
				if (rs.getDate("computer.discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("computer.discontinued").toLocalDate();
				}

				listComputers.add(new Computer.ComputerBuilder(rs.getString("computer.name"))
						.id(rs.getLong("computer.id")).introduceDate(introduced).discontinuedDate(discontinued)
						.company(new Company.CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
								.build())
						.build());

			}
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
		return listComputers;
	}

	public void create(Computer computer) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		try (PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, computer.getName());
			if (computer.getIntroducedDate() == null) {
				preparedStatement.setDate(2, null);
			} else {
				preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			}
			if (computer.getDiscontinuedDate() == null) {
				preparedStatement.setDate(3, null);
			} else {
				preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			}
			preparedStatement.setLong(4, computer.getCompany().getId());

			preparedStatement.executeUpdate();
			logger.info("Le produit a bien été crée.");
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

	public void update(int idComputer, Computer computer) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		try (PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.setInt(5, idComputer);
			preparedStatement.executeUpdate();
			logger.info("Le produit d'id : " + idComputer + " a bien été mis à jour.");
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

	public void deleteById(int idComputer) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		try (PreparedStatement preparedStatement = connect.prepareStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setInt(1, idComputer);
			preparedStatement.executeUpdate();
			logger.info("Le produit d'id : " + idComputer + " a bien été supprimé.");
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

	public void deleteByName(String nameComputer) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		try (PreparedStatement preparedStatement = connect.prepareStatement(DELETE_BY_NAME_SQL)) {
			preparedStatement.setString(1, nameComputer);
			preparedStatement.executeUpdate();
			logger.info("Le produit (" + nameComputer + ") a bien été supprimé.");
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

	public void deleteByCompanyId(int idCompany) throws IOException, SQLException, DBException {
		DaoComputer.connect = ConnexionMySQL.connect();
		try (PreparedStatement preparedStatement = connect.prepareStatement(DELETE_BY_COMPANY_ID_SQL)) {
			preparedStatement.setInt(1, idCompany);
			preparedStatement.executeUpdate();
			logger.info("Le(s) produit(s) d'idCompany : " + idCompany + " a(ont) bien été(s) supprimé(s).");
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} finally {
			try {
				DaoComputer.connect.close();
			} catch (SQLException sql) {
				logger.error(sql.getMessage());
				throw new DBException("La fermeture de la connexion à la base a echoué");
			}
		}
	}

	public void deleteSelection(String[] idTab) throws IOException, NumberFormatException, SQLException, DBException {
		for (int i = 0; i < idTab.length; i++) {
			if (!("".equals(idTab[i]))) {
				deleteById(Integer.parseInt(idTab[i]));
			}
		}
	}

}