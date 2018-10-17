package jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class ConnexionMySQL {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	private static String login = "admincdb";
	private static String password = "qwerty1234";

	private static Connection con;

	public static Connection getInstance() {
		if (con == null) {
			try {
				con = (Connection) DriverManager.getConnection(url, login, password);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return con;
	}

}
