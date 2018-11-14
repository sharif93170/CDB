package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyDtoMapper {

	private static CompanyDtoMapper companyDtoMapper = new CompanyDtoMapper();

	private CompanyDtoMapper() {
	}

	public static CompanyDtoMapper getInstance() {
		return companyDtoMapper;
	}

	public Company fromCompanyDTO(CompanyDTO companyDto) {
		Long id = Long.parseLong(companyDto.getId());
		String name = companyDto.getName();
		return new Company.CompanyBuilder(id).name(name).build();
	}

	public CompanyDTO toCompanyDTO(Company company) {
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setId(company.getId().toString());
		companyDto.setName(company.getName());
		return companyDto;
	}

}
