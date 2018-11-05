package com.excilys.cdb.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnexionMySQL {

//	private static final Logger logger = LoggerFactory.getLogger(ConnexionMySQL.class);

	private static Connection connect;
	private static HikariDataSource hikariDS;

	public static Connection getInstance() {

		Properties props = new Properties();
		try (FileInputStream in = new FileInputStream(
				"/home/excilys/eclipse-workspace/CDB/src/main/resources/config.properties")) {
			props.load(in);
		} catch (FileNotFoundException fnfe) {
//			logger.error("Fichier de config non trouvé !", fnfe);
		} catch (IOException io) {
//			logger.error("Erreur io exception !", io);
		}

		String url = props.getProperty("jdbc.url");
		String login = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		if (connect == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connect = DriverManager.getConnection(url, login, password);
//				logger.info("Connection à la base de données réussie !\n");
			} catch (SQLException sql) {
//				logger.error("SQL exception !", sql);
			} catch (ClassNotFoundException cnfe) {
//				logger.error("Class not found exception !", cnfe);
			}
		}
		return connect;

//		HikariConfig hikariConf = new HikariConfig("C:/Users/shari/eclipse-workspace/CDB/config.properties");
//		hikariDS = new HikariDataSource(hikariConf);
//
//		try {
//			Connection connect = hikariDS.getConnection();
//			logger.info("Connection à la base de données réussie !\n");
//			return connect;
//		} catch (Exception e) {
//			logger.error("Erreur !", e);
//			return null;
//		}

	}

}