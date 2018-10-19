package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private int idCompany;

	public Computer(Long id, String name, LocalDate introducedDate, LocalDate discontinuedDate, int idCompany) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.idCompany = idCompany;
	}
	
	public Computer(String name, LocalDate introducedDate, LocalDate discontinuedDate, int idCompany) {
		super();
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.idCompany = idCompany;
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

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	@Override
	public String toString() {
		return "Computer : id = " + id + ", Name = " + name + ", IntroducedDate = " + introducedDate + ", DiscontinuedDate = "
				+ discontinuedDate + ", idCompany = " + idCompany;
	}

}
