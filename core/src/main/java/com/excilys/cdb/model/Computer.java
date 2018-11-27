package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	private final Long id;
	private final String name;
	private final LocalDate introducedDate;
	private final LocalDate discontinuedDate;
	private final Company company;

	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.company = builder.company;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Company getCompany() {
		return company;
	}

	public static class ComputerBuilder {
		private Long id;
		private final String name;
		private LocalDate introducedDate;
		private LocalDate discontinuedDate;
		private Company company;

		public ComputerBuilder(String name) {
			this.name = name;
		}

		public ComputerBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder introduceDate(LocalDate introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}

		public ComputerBuilder company(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}

	}

	@Override
	public String toString() {
		return "Computer : id = " + id + ", Name = " + name + ", IntroducedDate = " + introducedDate
				+ ", DiscontinuedDate = " + discontinuedDate + ", Company = " + company.getId() + " - "
				+ company.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinuedDate == null) {
			if (other.discontinuedDate != null)
				return false;
		} else if (!discontinuedDate.equals(other.discontinuedDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introducedDate == null) {
			if (other.introducedDate != null)
				return false;
		} else if (!introducedDate.equals(other.introducedDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}