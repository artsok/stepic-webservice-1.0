package ru.stepic.webservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "online_user")
public class OnlineUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "sessionid")
	private String sessionid;
	 
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

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public OnlineUser() {
	}
	
	public OnlineUser(String login, String sessionid) {
		this.login = login;
		this.sessionid = sessionid;
	}
	
    @Override
    public String toString() {
        return "OnlineUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", sessionid=" + sessionid +
                '}';
    }
}
