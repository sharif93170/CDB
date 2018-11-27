package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int arg1) throws SQLException {

		return new Company.CompanyBuilder(rs.getLong("Id")).name(rs.getString("Name")).build();

	}

	public List<CompanyDTO> mapCompanyToCompanyDTO(List<Company> companies) {

		List<CompanyDTO> companyDTOs = new ArrayList<CompanyDTO>();
		for (Company indexCompany : companies) {
			companyDTOs.add(new CompanyDTO(indexCompany));
		}
		return companyDTOs;

	}

}