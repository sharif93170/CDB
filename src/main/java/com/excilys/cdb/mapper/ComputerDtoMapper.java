package com.excilys.cdb.mapper;

import java.time.LocalDate;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDtoMapper {

	private static ComputerDtoMapper computerDtoMapper = new ComputerDtoMapper();

	private ComputerDtoMapper() {
	}

	public static ComputerDtoMapper getInstance() {
		return computerDtoMapper;
	}

	public Computer fromComputerDTO(ComputerDTO computerDto) {

		String computerName = computerDto.getName();
		Long computerId = null;
		LocalDate introduced = null, discontinued = null;

		if (computerDto.getId() != null) {
			computerId = Long.parseLong(computerDto.getId());
		}
		if (!"".equals(computerDto.getIntroduced())) {
			introduced = LocalDate.parse(computerDto.getIntroduced());
		}
		if (!"".equals(computerDto.getDiscontinued())) {
			discontinued = LocalDate.parse(computerDto.getDiscontinued());
		}

		Long companyId = Long.parseLong(computerDto.getCompanyId());
		String companyName = computerDto.getCompanyName();

		return new Computer.ComputerBuilder(computerName).id(computerId).introduceDate(introduced)
				.discontinuedDate(discontinued).company(new Company.CompanyBuilder(companyId).name(companyName).build())
				.build();

	}

	public ComputerDTO toComputerDTO(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO();

		computerDto.setId(computer.getId() + "");
		computerDto.setName(computer.getName());

		if (computer.getIntroducedDate() != null) {
			computerDto.setIntroduced(computer.getIntroducedDate().toString());
		}
		if (computer.getDiscontinuedDate() != null) {
			computerDto.setDiscontinued(computer.getDiscontinuedDate().toString());
		}

		computerDto.setCompanyId(computer.getCompany().getId() + "");
		computerDto.setCompanyName(computer.getCompany().getName());

		return computerDto;

	}

}
