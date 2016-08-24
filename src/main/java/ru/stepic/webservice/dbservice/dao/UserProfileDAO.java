package ru.stepic.webservice.dbservice.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.datasets.UserProfileDataSet;
import ru.stepic.webservice.dbservice.executor.Executor;

public class UserProfileDAO {

	private Executor executor;
	
	private static final Logger log = LoggerFactory.getLogger(UserProfileDAO.class);  
	
	public UserProfileDAO(Connection connection) {
		this.executor = new Executor(connection);
	}  
	
	public void createTable() throws SQLException {
		executor.execUpdate("create table if not exists users "
				+ "(id bigint auto_increment, login varchar(256), password varchar(256), primary key (id))");
		log.debug("create table - 'users'");
	}

	public void insertUser(String login, String password) throws SQLException {
		executor.execUpdate("insert into users (login, password) values ('" + login + "', '" + password + "')");
	}
	
	
 	public UserProfileDataSet get(String login) throws SQLException {
		return executor.execQuery("select * from users where login='" + login + "'", result -> {
			if(result.next()) {
				return new UserProfileDataSet(result.getLong(1), result.getString(2), result.getString(3));
			}
			log.info("No result find");
			return null;
		});
	}
	
	public long getUserProfileId(String login) throws SQLException {
		return executor.execQuery("select id from users where login ='" + login + "'", result -> {
			result.next();
			return result.getLong(1); 
		});
	}
	

	
	
}
