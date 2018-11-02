package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;

	public Computer(Long id, String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = company;
	}

	public Computer(String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		super();
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = company;
	}

	public Computer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
<<<<<<< HEAD:src/main/java/com/excilys/cdb/model/Computer.java
		return "Computer : id = " + id + ", Name = " + name + ", IntroducedDate = " + introducedDate
				+ ", DiscontinuedDate = " + discontinuedDate + ", Company = " + company.getId() + " - "
				+ company.getName();
=======
		return "Computer : id = " + id + ", Name = " + name + ", IntroducedDate = " + introducedDate + ", DiscontinuedDate = "
				+ discontinuedDate + ", idCompany = " + idCompany;
>>>>>>> master:src/model/Computer.java
	}

}