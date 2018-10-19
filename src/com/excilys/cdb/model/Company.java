package com.excilys.cdb.model;

public class Company {

	private Long id;
	private String name;

	public Company(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getiD() {
		return id;
	}

	public void setiD(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company : id = " + id + ", Name = " + name;
	}

}
