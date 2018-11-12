package com.excilys.cdb.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb.dao")
public class DataSourceConfig {

	Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Bean
	public DataSource dataSource() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("config.properties");
		Properties props = new Properties();
		try {
			props.load(input);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(props.getProperty("driverClassName"));
		config.setJdbcUrl(props.getProperty("JdbcUrl"));
		config.setUsername(props.getProperty("dataSource.user"));
		config.setPassword(props.getProperty("dataSource.password"));
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}

}
