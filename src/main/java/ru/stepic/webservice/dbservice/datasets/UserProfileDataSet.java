package ru.stepic.webservice.dbservice.datasets;

public class UserProfileDataSet {
	private long id;
	private String login;
	private String password;
	
	public UserProfileDataSet(long id, String login, String password) {
		this.setId(id);
		this.setLogin(login);
		this.setPassword(password);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserDataSet{" + "id=" + id +", login = " + login +  '\'' + "}";
	}

}
