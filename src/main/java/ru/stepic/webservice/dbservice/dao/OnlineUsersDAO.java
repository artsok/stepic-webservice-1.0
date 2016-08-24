package ru.stepic.webservice.dbservice.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.datasets.OnlineUsersDataSet;
import ru.stepic.webservice.dbservice.executor.Executor;

public class OnlineUsersDAO {
	
	private Executor executor;
	
	private static final Logger log = LoggerFactory.getLogger(OnlineUsersDAO.class);  
	
	public OnlineUsersDAO(Connection connection) {
		this.executor = new Executor(connection);
	}
	
	public void createTable() throws SQLException {
		executor.execUpdate("create table if not exists online_users "
				+ "(id bigint auto_increment, login varchar(256), sessionid varchar(256), primary key (id))");
		log.debug("create table - 'online_users'");
	}
	
	public void insertUser(String login, String sessionid) throws SQLException {
		executor.execUpdate("insert into online_users (login, sessionid) "
				+ "values ('" + login + "', '" + sessionid + "')");
		log.debug("insert values into online_users. Values" + new String[] {login, sessionid});
	}
	
 	public OnlineUsersDataSet getUserBySessionId(String sessionID) throws SQLException {
		return executor.execQuery("select * from online_users where sessionid='" + sessionID + "'", result -> {
			result.next();
			return new OnlineUsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
		});
	}
 	
	public OnlineUsersDataSet getUserByLogin(String login) throws SQLException {
		return executor.execQuery("select * from online_users where login='" + login + "'", result -> {
			if(result.next()) {
				return new OnlineUsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
			}
			return null;
		});
	}
 	
	public long getOnlineUserProfileId(String login) throws SQLException {
		return executor.execQuery("select id from online_users where login ='" + login + "'", result -> {
			result.next();
			return result.getLong(1); 
		});
	}
}
