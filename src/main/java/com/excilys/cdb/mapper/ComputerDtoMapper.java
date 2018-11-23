package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerDtoMapper {

	public ArrayList<ComputerDTO> mappToListComputerDTO(ResultSet rs) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();

		try {
			while (rs.next()) {
				listComputers.add(new ComputerDTO(rs.getInt(1), rs.getString(2), convertDateToString(rs.getDate(3)),
						convertDateToString(rs.getDate(4)), rs.getInt(5), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listComputers;
	}

	public ComputerDTO mappToComputerDTO(ResultSet rs) {
		ComputerDTO cpDTO = null;
		try {
			cpDTO = new ComputerDTO(rs.getInt(1), rs.getString(2), convertDateToString(rs.getDate(3)),
					convertDateToString(rs.getDate(4)), rs.getInt(5), rs.getString(7));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cpDTO;
	}

	public ArrayList<ComputerDTO> ComputersToComputersDTO(ArrayList<Computer> list) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();

		for (Computer computer : list) {
			listComputers.add(new ComputerDTO(computer.getId().intValue(), computer.getName(),
					convertLocalDateToString(computer.getIntroducedDate()),
					convertLocalDateToString(computer.getDiscontinuedDate()),
					getCompanyId(computer.getCompany()).intValue(), getCompanyName(computer.getCompany())));
		}
		return listComputers;

	}

	public Long getCompanyId(Company company) {
		if (company == null) {
			return (long) 0;
		} else {
			return company.getId();
		}
	}

	public String getCompanyName(Company company) {
		if (company == null) {
			return "";
		} else {
			return company.getName();
		}
	}

	public String convertLocalDateToString(LocalDate date) {
		if (date == null) {
			return "";
		} else {
			return date.toString();
		}
	}

	public String convertDateToString(Date date) {
		if (date == null) {
			return "";
		} else {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		}
	}
}
