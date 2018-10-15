package model;

public class Company {

	private String iD;
	private String name;

	public Company(String iD, String name) {
		super();
		this.iD = iD;
		this.name = name;
	}

	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [iD=" + iD + ", name=" + name + "]";
	}

}
