package com.excilys.cdb.dto;

import com.excilys.cdb.model.Company;

public class CompanyDTO {

	private long id;
	private String name;

	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}