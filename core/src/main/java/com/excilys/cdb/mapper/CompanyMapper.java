package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {

	public Company mappToCompany(int id, String name) {
		return new Company.CompanyBuilder((long) id).name(name).build();
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