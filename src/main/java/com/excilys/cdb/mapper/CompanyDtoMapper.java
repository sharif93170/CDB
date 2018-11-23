package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyDtoMapper {

	public Company mappToCompany(Long id, String name) {
		return new Company.CompanyBuilder(id).name(name).build();
	}

	public Company mappToCompany(ResultSet rs) {
		Company company = null;
		try {
			company = new Company.CompanyBuilder(rs.getLong(1)).name(rs.getString(2)).build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}
