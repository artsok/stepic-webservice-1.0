package ru.stepic.webservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "login", unique=true, nullable=false) 
	private String login;
	
	@Column(name = "password", unique=true, nullable=false)
	private String password;
	 
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

	public UserProfile() {
	}
	
	public UserProfile(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password=" + password +
                '}';
    }
}
