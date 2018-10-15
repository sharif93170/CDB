package persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class ConnexionMySQL {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	private static String login = "admincdb";
	private static String password = "qwerty1234";

	private static Connection con;

	public static Connection getInstance() {
		try {
			con = (Connection) DriverManager.getConnection(url, login, password);
			System.out.println("Connection reussie !");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return con;
	}

	public static void main(String[] args) {
		ConnexionMySQL.getInstance();
	}

}
