package ru.stepic.webservice.dbservice.datasets;

public class OnlineUsersDataSet {

	private long id;
	private String login;
	private String sessionId;
	
	public OnlineUsersDataSet(long id, String login, String sessionId) {
		this.setId(id);
		this.setLogin(login);
		this.setSessionId(sessionId);
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public String toString() {
		return "OnlineUserProfileDataSet{" + "id=" + id +", login = " + login + ", sessionId = " +  sessionId + '\'' + "}";
	}
}
