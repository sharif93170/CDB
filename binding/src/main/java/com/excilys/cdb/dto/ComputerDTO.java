package com.excilys.cdb.dto;

import com.excilys.cdb.model.Computer;

public class ComputerDTO {

	private long id;
	private String introduced;
	private String discontinued;
	private long companyId;
	private String name;

	public ComputerDTO(Computer computer) {
		this.id = computer.getId();
		this.name = computer.getName();
		this.introduced = computer.getIntroducedDate().toString();
		this.discontinued = computer.getDiscontinuedDate().toString();
		this.companyId = computer.getCompany().getId();
	}

	public ComputerDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + ", name=" + name + "]";
	}

}