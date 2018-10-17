package model;

public class Company {

	private int id;
	private String name;

	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getiD() {
		return id;
	}

	public void setiD(int id) {
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
