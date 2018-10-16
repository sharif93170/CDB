package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jdbc.ConnexionMySQL;
import model.Computer;

public class DaoComputer {

	static DaoComputer daoComputer = new DaoComputer();
	Connection conn;

	private DaoComputer() {
		conn = ConnexionMySQL.getInstance();
	}

	public static DaoComputer getInstance() {
		return daoComputer;
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
			listComputers.stream().forEach(System.out::println);
			return listComputers;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} finally {
			rs.close();
			preparedStatement.close();
		}

	}

	public void create(Computer computer) {
		PreparedStatement preparedStatement = null;
	}

	public void update(int idComputer) {
		PreparedStatement preparedStatement = null;
	}

	public void delete(int idComputer) {
		PreparedStatement preparedStatement = null;
	}

}
