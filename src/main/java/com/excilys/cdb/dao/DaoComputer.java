package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DaoComputer {

//	static Logger logger = LoggerFactory.getLogger(DaoComputer.class);

	private static String SELECT_DETAILS_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE computer.id = ?";
	private static String SELECT_ALL_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id";
	private static String SELECT_BY_NAME_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE computer.name = ?";
	private static String INSERT_SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static String UPDATE_SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static String DELETE_BY_ID_SQL = "DELETE FROM computer WHERE id = ?";
	private static String DELETE_BY_NAME_SQL = "DELETE FROM computer WHERE name = ?";

	static DaoComputer daoComputer = new DaoComputer();
	Connection connect;

	private DaoComputer() {
		connect = ConnexionMySQL.getInstance();
	}

	public static DaoComputer getInstance() {
		return daoComputer;
	}

	public Computer showDetails(int idComputer) {
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
//			logger.error("SQL exception !", sql);
		}
		return computer;
	}

	public ArrayList<Computer> findByName(String name) throws SQLException, PremierePageException {
		ResultSet rs = null;
		ArrayList<Computer> listComputers = new ArrayList<>();
		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_BY_NAME_SQL)) {
			preparedStatement.setString(1, name);
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
//			logger.error("SQL exception !", sql);
		}
		return listComputers;
	}

	public ArrayList<Computer> findAll() throws SQLException, PremierePageException {

		ArrayList<Computer> listComputers = new ArrayList<>();
		try (PreparedStatement preparedStatement = connect.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = preparedStatement.executeQuery()) {
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
//			logger.error("SQL exception !", sql);
		}
		return listComputers;
	}

	public void create(Computer computer) {
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
//			logger.info("Le produit a bien été crée.");
		} catch (SQLException sql) {
//			logger.error("SQL exception !", sql);
		}
	}

	public void update(int idComputer, Computer computer) {
		try (PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.setInt(5, idComputer);
			preparedStatement.executeUpdate();
//			logger.info("Le produit d'id : " + idComputer + " a bien été mis à jour.");
		} catch (SQLException sql) {
//			logger.error("SQL exception !", sql);
		}
	}

	public void deleteById(int idComputer) {
		try (PreparedStatement preparedStatement = connect.prepareStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setInt(1, idComputer);
			preparedStatement.executeUpdate();
//			logger.info("Le produit d'id : " + idComputer + " a bien été supprimé.");
		} catch (SQLException sql) {
//			logger.error("SQL exception !", sql);
		}
	}

	public void deleteByName(String nameComputer) {
		try (PreparedStatement preparedStatement = connect.prepareStatement(DELETE_BY_NAME_SQL)) {
			preparedStatement.setString(1, nameComputer);
			preparedStatement.executeUpdate();
//			logger.info("Le produit (" + nameComputer + ") a bien été supprimé.");
		} catch (SQLException sql) {
//			logger.error("SQL exception !", sql);
		}
	}

}