package com.excilys.cdb.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class ConnexionMySQL {

	private static Connection connect;

	public static Connection getInstance() {

		Properties props = new Properties();
		try (FileInputStream in = new FileInputStream("/home/excilys/eclipse-workspace/CDB/src/main/resources/config.properties")) {
			props.load(in);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = props.getProperty("jdbc.url");
		String login = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		if (connect == null) {
			try {
				connect = DriverManager.getConnection(url, login, password);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return connect;
	}

}
