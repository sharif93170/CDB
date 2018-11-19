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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb.dao, " + "com.excilys.cdb.service, " + "com.excilys.cdb.validator")
public class RootConfig {

	Logger logger = LoggerFactory.getLogger(RootConfig.class);

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
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(props.getProperty("driverClassName"));
		hikariConfig.setJdbcUrl(props.getProperty("JdbcUrl"));
		hikariConfig.setUsername(props.getProperty("dataSource.user"));
		hikariConfig.setPassword(props.getProperty("dataSource.password"));
		HikariDataSource hikariDS = new HikariDataSource(hikariConfig);
		return hikariDS;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		return transactionManager;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}
