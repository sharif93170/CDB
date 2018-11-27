package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int arg1) throws SQLException {

		Long id = rs.getLong("Id");
		String name = rs.getString("Name");
		LocalDate introduced = null, discontinued = null;
		Long companyId = null;
		String companyName = null;
		if (rs.getDate("introduced") != null) {
			introduced = rs.getDate("introduced").toLocalDate();
		}
		if (rs.getDate("discontinued") != null) {
			discontinued = rs.getDate("discontinued").toLocalDate();
		}
		if (rs.getInt("company.Id") != 0) {
			companyId = rs.getLong("company.Id");
			companyName = rs.getString("company.name");
		}
		return new Computer.ComputerBuilder(name).id(id).introduceDate(introduced).discontinuedDate(discontinued)
				.company(new Company.CompanyBuilder(companyId).name(companyName).build()).build();

	}

	public List<ComputerDTO> mapComputerToComputerDTO(List<Computer> computers) {

		List<ComputerDTO> companyDTOs = new ArrayList<ComputerDTO>();
		for (Computer indexComputer : computers) {
			companyDTOs.add(new ComputerDTO(indexComputer));
		}
		return companyDTOs;

	}

}
