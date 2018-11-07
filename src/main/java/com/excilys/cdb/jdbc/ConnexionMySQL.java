package com.excilys.cdb.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class ConnexionMySQL {

	private static final Logger logger = LoggerFactory.getLogger(ConnexionMySQL.class);

	private static Connection connect;
	private final static ConnexionMySQL connexionMySql = new ConnexionMySQL();

	private static HikariConfig hikariConf = new HikariConfig();
	private static HikariDataSource hikariDS;

	private ConnexionMySQL() {
		hikariConf = new HikariConfig("/home/excilys/eclipse-workspace/CDB/src/main/resources/config.properties");
		hikariDS = new HikariDataSource(hikariConf);
	}

	public static Connection connect() throws IOException {

		try {
			Connection connect = hikariDS.getConnection();
			logger.info("Connexion à la base de donnée réussie !");
			return connect;
		} catch (SQLException sql) {
			logger.error("Echec de la connexion.");
			hikariDS.close();
			return null;
		}
	}

	public static ConnexionMySQL getInstance() {
		return connexionMySql;
	}

	public static Connection getConnection() {
		return connect;
	}

}