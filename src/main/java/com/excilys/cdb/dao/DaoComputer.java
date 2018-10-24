package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

import com.excilys.cdb.jdbc.ConnexionMySQL;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DaoComputer {

	private static String SELECT_DETAILS_SQL = "SELECT cpt.id, cpt.name, cpt.introduced, cpt.discontinued, cpn.id, cpn.name FROM computer cpt, company cpn WHERE cpt.id = ? AND cpt.company_id = cpn.id";
	private static String SELECT_ALL_SQL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static String INSERT_SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static String UPDATE_SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static String DELETE_BY_ID_SQL = "DELETE FROM computer WHERE id = ?";
	private static String DELETE_BY_NAME_SQL = "DELETE FROM computer WHERE name = ?";

	static DaoComputer daoComputer = new DaoComputer();
	Connection conn;

	private DaoComputer() {
		conn = ConnexionMySQL.getInstance();
	}

	public static DaoComputer getInstance() {
		return daoComputer;
	}

	public Computer showDetails(int idComputer) throws SQLException {
		ResultSet rs = null;
		Computer cpt = null;
		try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_DETAILS_SQL)) {
			preparedStatement.setInt(1, idComputer);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				LocalDate introduced;
				LocalDate discontinued;
				if (rs.getDate("cpt.introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("cpt.introduced").toLocalDate();
				}
				if (rs.getDate("cpt.discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("cpt.discontinued").toLocalDate();
				}

				cpt = new Computer(rs.getLong("cpt.id"), rs.getString("cpt.name"), introduced, discontinued,
						new Company(rs.getLong("cpn.id"), rs.getString("cpn.name")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return cpt;
	}

	public ArrayList<Computer> findAll() throws SQLException {

		try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = preparedStatement.executeQuery()) {
			ArrayList<Computer> listComputers = new ArrayList<>();
			while (rs.next()) {

				LocalDate introduced, discontinued;

				if (rs.getDate("cpt.introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("cpt.introduced").toLocalDate();
				}
				if (rs.getDate("cpt.discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("cpt.discontinued").toLocalDate();
				}

				listComputers.add(new Computer(rs.getLong("cpt.id"), rs.getString("cpt.name"), introduced, discontinued,
						new Company(rs.getLong("cpn.id"), rs.getString("cpn.name"))));
			}
			return listComputers;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}

	}

	public boolean create(Computer computer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.executeUpdate();
			System.out.println("Le produit a bien été crée.");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean update(int idComputer, Computer computer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.setInt(5, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été mis à jour.\n");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean deleteById(int idComputer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setInt(1, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été supprimé.");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean deleteByName(String nameComputer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BY_NAME_SQL)) {
			preparedStatement.setString(1, nameComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit (" + nameComputer + ") a bien été supprimé.");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

}