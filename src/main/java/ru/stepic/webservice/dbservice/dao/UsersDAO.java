package ru.stepic.webservice.dbservice.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.datasets.UsersDataSet;
import ru.stepic.webservice.dbservice.executor.Executor;

public class UsersDAO {
	
	private Executor executor;
	
	private static final Logger log = LoggerFactory.getLogger(UsersDAO.class); 

	public UsersDAO(Connection connection) {
		this.executor = new Executor(connection);
	}  

 	public UsersDataSet get(long id) throws SQLException {
		return executor.execQuery("select * from users where id=" + id, result -> {
			result.next();
			return new UsersDataSet(result.getLong(1), result.getString(2));
		});
	}

	public void dropTable() throws SQLException {
		executor.execUpdate("drop table users");
		
	}

	public void createTable() throws SQLException {
		executor.execUpdate("create table if not exists users (id bigint auto_increment, user_name varchar(256), primary key (id))");
		log.debug("create table");
	}

	public void insertUser(String name) throws SQLException {
		executor.execUpdate("insert into users (user_name) values ('" + name + "')");
	}

	/**
	 * Return user's id, which we created
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public long getUserId(String name) throws SQLException {
		return executor.execQuery("select * from users where user_name='" + name + "'", result -> {
			result.next();
			return result.getLong(1); 
		});
	}

}
