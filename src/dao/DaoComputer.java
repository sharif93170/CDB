package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

import jdbc.ConnexionMySQL;
import model.Computer;

public class DaoComputer {

	static DaoComputer daoComputer = new DaoComputer();
	Connection conn;
	private ResultSet rs;

	private DaoComputer() {
		conn = ConnexionMySQL.getInstance();
	}

	public static DaoComputer getInstance() {
		return daoComputer;
	}

	public void showDetails(int idComputer) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM computer WHERE id = ?");
			preparedStatement.setInt(1, idComputer);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				LocalDate introduced;
				LocalDate discontinued;
				if (rs.getDate(3) == null) {
					introduced = null;
				} else {
					introduced = rs.getDate(3).toLocalDate();
				}
				if (rs.getDate(4) == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate(4).toLocalDate();
				}
				System.out.println("Id = " + rs.getInt(1) + ", Name = " + rs.getString(2) + ", Introduced = "
						+ introduced + ", Discontinued = " + discontinued + ", IdCompany = " + rs.getInt(5) + ".");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			rs.close();
			preparedStatement.close();
		}
	}

	public ArrayList<Computer> getListComputers() throws SQLException {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM computer");
			rs = preparedStatement.executeQuery();
			ArrayList<Computer> listComputers = new ArrayList<>();
			while (rs.next()) {

				LocalDate introduced;
				LocalDate discontinued;

				if (rs.getDate(3) == null) {
					introduced = null;
				} else {
					introduced = rs.getDate(3).toLocalDate();
				}
				if (rs.getDate(4) == null) {
					discontinued = null;
				} else {
					discontinued = rs.getDate(4).toLocalDate();
				}

				listComputers.add(new Computer(rs.getInt(1), rs.getString(2), introduced, discontinued, rs.getInt(5)));
			}

			System.out.println("Success !");
			return listComputers;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		} finally {
			rs.close();
			preparedStatement.close();
		}

	}

	public void create(Computer computer) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(
					"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setInt(4, computer.getIdCompany());
			preparedStatement.executeUpdate();
			System.out.println("Le produit a bien été crée.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void update(int idComputer, Computer computer) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(
					"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroducedDate()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinuedDate()));
			preparedStatement.setInt(4, computer.getIdCompany());
			preparedStatement.setInt(5, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été mis à jour.\n");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void deleteById(int idComputer) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
			preparedStatement.setInt(1, idComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit d'id : " + idComputer + " a bien été supprimé.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void deleteByName(String nameComputer) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM computer WHERE name = ?");
			preparedStatement.setString(1, nameComputer);
			preparedStatement.executeUpdate();
			System.out.println("Le produit (" + nameComputer + ") a bien été supprimé.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

}
