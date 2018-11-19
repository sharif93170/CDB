package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public class DaoCompany {

	private final static String SELECT_ALL_SQL = "SELECT id, name FROM company";
	private final static String DELETE_COMPANY_BY_ID_SQL = "DELETE FROM company WHERE id= :id";

	private final DataSource dataSource;

	public DaoCompany(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Company> findAll() {
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

	public void delete(int idCompany) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", idCompany);
		jdbcTemplate.update(DELETE_COMPANY_BY_ID_SQL, params);
	}

}