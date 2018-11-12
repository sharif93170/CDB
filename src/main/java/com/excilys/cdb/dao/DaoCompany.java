package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.model.Company;

@Repository
public class DaoCompany {

	private static final Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	private final static String SELECT_ALL_SQL = "SELECT id, name FROM company";
	private final static String DELETE_COMPANY_BY_ID_SQL = "DELETE FROM company WHERE id= :id";

	@Autowired
	DataSource dataSource;

	public List<Company> findAll() throws SQLException, IOException, DBException {
		List<Company> listCompanies = new ArrayList<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		RowMapper<Company> rowMapper = new RowMapper<Company>() {
			public Company mapRow(ResultSet rs, int pRowNum) throws SQLException {
				Company company = new Company.CompanyBuilder(rs.getLong("id")).name(rs.getString("name")).build();
				return company;
			}
		};

		listCompanies = jdbcTemplate.query(SELECT_ALL_SQL, rowMapper);
		return listCompanies;
	}

	public void delete(int idCompany) throws IOException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", idCompany);
		jdbcTemplate.update(DELETE_COMPANY_BY_ID_SQL, params);
	}

}