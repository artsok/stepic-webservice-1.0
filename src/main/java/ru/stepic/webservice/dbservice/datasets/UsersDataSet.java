package ru.stepic.webservice.dbservice.datasets;

public class UsersDataSet {

	private long id;
	private String name;
	
	public UsersDataSet(long id, String name) {
		this.setId(id);
		this.setName(name);
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
	
	@Override
	public String toString() {
		return "UserDataSet{" + "id=" + id +", name = " + name + '\'' + "}";
	}
	
}
