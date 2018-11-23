package com.excilys.cdb.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class ComputerDTO {

	private int id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String introduced;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String discontinued;
	private int companyId;
	private String companyName;

	public ComputerDTO() {
		super();
	}

	public ComputerDTO(int id, String name, String introduced, String discontinued, int companyId, String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
