package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.model.Computer.ComputerBuilder;

@Repository
public class DaoComputer {

	static Logger logger = LoggerFactory.getLogger(DaoComputer.class);

	private final static String COUNT_SQL = "SELECT COUNT(computer.id) FROM computer AS computer LEFT JOIN company AS company ON computer.company_id = company.id WHERE UPPER(computer.name) LIKE UPPER(:name) OR UPPER(company.name) LIKE UPPER(:name) ";
	private final static String SELECT_DETAILS_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE computer.id = :id";
	private final static String SELECT_ALL_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final static String SELECT_BY_NAME_SQL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer computer LEFT JOIN company company ON computer.company_id = company.id WHERE UPPER(computer.name) LIKE UPPER(:name) OR UPPER(company.name) LIKE UPPER(:name) ORDER BY computer.name LIMIT :limit OFFSET :offset";
	private final static String INSERT_SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :company_id)";
	private final static String UPDATE_SQL = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	private final static String DELETE_BY_ID_SQL = "DELETE FROM computer WHERE id = :id";
	private final static String DELETE_BY_COMPANY_ID_SQL = "DELETE FROM computer WHERE company_id = :company_id";
	private final static String DELETE_BY_NAME_SQL = "DELETE FROM computer WHERE name = :name";

	@Autowired
	DataSource dataSource;

	public int count(String name) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", "%" + name + "%");
		int count = jdbcTemplate.queryForObject(COUNT_SQL, params, Integer.class);
		return count;

	}

	public Computer showDetails(int idComputer) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", idComputer);

		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet rs, int pRowNum) throws SQLException {
				Computer computer;
				ComputerBuilder cptBuilder = new Computer.ComputerBuilder(rs.getString("computer.name"))
						.id(rs.getLong("computer.id"));
				if (rs.getDate("computer.introduced") != null) {
					cptBuilder.introduceDate(rs.getDate("computer.introduced").toLocalDate());
				}
				if (rs.getDate("computer.discontinued") != null) {
					cptBuilder.discontinuedDate(rs.getDate("computer.discontinued").toLocalDate());
				}
				Company company = new CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
						.build();
				cptBuilder.company(company);
				computer = cptBuilder.build();

				return computer;
			}
		};

		return jdbcTemplate.queryForObject(SELECT_DETAILS_SQL, params, rowMapper);
	}

	public List<Computer> findByName(String name, int page, int size)
			throws SQLException, PremierePageException, IOException, DBException {
		List<Computer> listComputers = new ArrayList<>();

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", "%" + name + "%");
		params.addValue("limit", size);
		params.addValue("offset", (page - 1) * size);

		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet rs, int pRowNum) throws SQLException {
				Computer computer;
				ComputerBuilder cptBuilder = new Computer.ComputerBuilder(rs.getString("computer.name"))
						.id(rs.getLong("computer.id"));
				if (rs.getDate("computer.introduced") != null) {
					cptBuilder.introduceDate(rs.getDate("computer.introduced").toLocalDate());
				}
				if (rs.getDate("computer.discontinued") != null) {
					cptBuilder.discontinuedDate(rs.getDate("computer.discontinued").toLocalDate());
				}
				Company company = new CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
						.build();
				cptBuilder.company(company);
				computer = cptBuilder.build();

				return computer;
			}
		};

		listComputers = jdbcTemplate.query(SELECT_BY_NAME_SQL, params, rowMapper);
		return listComputers;

	}

	public List<Computer> findAll(int page, int size)
			throws SQLException, PremierePageException, IOException, DBException {
		List<Computer> listComputers = new ArrayList<>();

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", size);
		params.addValue("offset", (page - 1) * size);

		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet rs, int pRowNum) throws SQLException {
				Computer computer;
				ComputerBuilder cptBuilder = new Computer.ComputerBuilder(rs.getString("computer.name"))
						.id(rs.getLong("computer.id"));
				if (rs.getDate("computer.introduced") != null) {
					cptBuilder.introduceDate(rs.getDate("computer.introduced").toLocalDate());
				}
				if (rs.getDate("computer.discontinued") != null) {
					cptBuilder.discontinuedDate(rs.getDate("computer.discontinued").toLocalDate());
				}
				Company company = new CompanyBuilder(rs.getLong("company.id")).name(rs.getString("company.name"))
						.build();
				cptBuilder.company(company);
				computer = cptBuilder.build();

				return computer;
			}
		};

		listComputers = jdbcTemplate.query(SELECT_ALL_SQL, params, rowMapper);
		return listComputers;

	}

	public void create(Computer computer) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", computer.getName(), Types.VARCHAR);
		params.addValue("introduced", computer.getIntroducedDate() == null ? null : computer.getIntroducedDate(),
				Types.DATE);
		params.addValue("discontinued", computer.getDiscontinuedDate() == null ? null : computer.getDiscontinuedDate(),
				Types.DATE);
		params.addValue("company_id", computer.getCompany().getId() == 0 ? null : computer.getCompany().getId(),
				Types.LONGNVARCHAR);
		params.addValue("id", computer.getId(), Types.LONGNVARCHAR);
		jdbcTemplate.update(INSERT_SQL, params);
	}

	public void update(int idComputer, Computer computer) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", computer.getName(), Types.VARCHAR);
		params.addValue("introduced", computer.getIntroducedDate() == null ? null : computer.getIntroducedDate(),
				Types.DATE);
		params.addValue("discontinued", computer.getDiscontinuedDate() == null ? null : computer.getDiscontinuedDate(),
				Types.DATE);
		params.addValue("company_id", computer.getCompany().getId() == 0 ? null : computer.getCompany().getId(),
				Types.LONGNVARCHAR);
		params.addValue("id", computer.getId(), Types.LONGNVARCHAR);
		jdbcTemplate.update(UPDATE_SQL, params);
	}

	public void deleteById(int idComputer) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", idComputer);
		jdbcTemplate.update(DELETE_BY_ID_SQL, params);
	}

	public void deleteByName(String nameComputer) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", nameComputer);
		jdbcTemplate.update(DELETE_BY_NAME_SQL, params);
	}

	public void deleteByCompanyId(int idCompany) throws IOException, SQLException, DBException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("company_id", idCompany);
		jdbcTemplate.update(DELETE_BY_COMPANY_ID_SQL, params);
	}

	public void deleteSelection(String[] idTab) throws IOException, NumberFormatException, SQLException, DBException {
		for (int i = 0; i < idTab.length; i++) {
			if (!("".equals(idTab[i]))) {
				deleteById(Integer.parseInt(idTab[i]));
			}
		}
	}

}