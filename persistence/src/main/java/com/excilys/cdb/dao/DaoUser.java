package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;
import com.excilys.cdb.model.User.UserBuilder;
import com.excilys.cdb.model.Role;

@Repository
public class DaoUser {

	private final static String FIND_ALL_USERS_SQL = "SELECT id, name, password, role FROM user";

	private final DataSource dataSource;

	public DaoUser(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User find(String username) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);

		RowMapper<User> rowMapper = new RowMapper<User>() {
			public User mapRow(ResultSet rs, int pRowNum) throws SQLException {
				User user;
				Role role = new Role();
				role.setName(rs.getString("role"));
				UserBuilder usrBuilder = new User.UserBuilder(rs.getString("name")).id(rs.getLong("id"))
						.password(rs.getString("password")).role(role);
				user = usrBuilder.build();

				return user;
			}
		};

		return jdbcTemplate.queryForObject(FIND_ALL_USERS_SQL, params, rowMapper);
	}

}
