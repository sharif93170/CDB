package com.excilys.cdb.model;

public class Company {

	private Long id;
	private String name;

	public Company(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Company(Long id) {
		super();
		this.id = id;
	}

	public Company() {
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

	@Override
	public String toString() {
		return "Company : id = " + id + ", Name = " + name;
	}

}