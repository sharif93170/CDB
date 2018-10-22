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

	public void showDetails(int idComputer) throws SQLException {
		ResultSet rs = null;
		try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_DETAILS_SQL)) {
			preparedStatement.setInt(1, idComputer);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				LocalDate introduced;
				LocalDate discontinued;
				if (rs.getDate("introduced") == null) {
					introduced = null;
				} else {
					introduced = rs.getDate("introduced").toLocalDate();
				}
				if (rs.getDate("discontinued") == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate("discontinued").toLocalDate();
				}
				System.out.println("Id = " + rs.getLong("id") + ", Name = " + rs.getString("name") + ", Introduced = "
						+ introduced + ", Discontinued = " + discontinued + ", IdCompany = " + rs.getInt("company_id")
						+ ".");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Computer> findAll() throws SQLException {

		try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = preparedStatement.executeQuery()) {
			ArrayList<Computer> listComputers = new ArrayList<>();
			while (rs.next()) {

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

				listComputers.add(new Computer(rs.getLong("cpt.id"), rs.getString("cpt.name"), introduced, discontinued,
						new Company(rs.getLong("cpn.id"), rs.getString("cpn.name"))));
			}

			System.out.println("Success !");
			return listComputers;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}

	}

	public void create(Computer computer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.executeUpdate();
			System.out.println("Le produit a bien été crée.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void update(int idComputer, Computer computer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setLong(4, computer.getCompany().getId());
			preparedStatement.setInt(5, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été mis à jour.\n");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void deleteById(int idComputer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BY_ID_SQL)) {
			preparedStatement.setInt(1, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été supprimé.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void deleteByName(String nameComputer) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BY_NAME_SQL)) {
			preparedStatement.setString(1, nameComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit (" + nameComputer + ") a bien été supprimé.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

}