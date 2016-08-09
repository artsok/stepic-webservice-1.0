package ru.stepic.webservice.dbservice.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.DbService;


public class Executor {

	private final Connection connection;
	
	private static final Logger log = LoggerFactory.getLogger(Executor.class);
	
	public Executor(Connection connection) {
		this.connection = connection;
	}

	public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
		log.info("execQuery: " + query);
		Statement stmt = connection.createStatement();
		stmt.execute(query);
		ResultSet resultSet = stmt.getResultSet();
		T value = handler.handle(resultSet);
		resultSet.close();
		stmt.close();
		return value;
	}

	public int execUpdate(String query) throws SQLException {
		log.info("execUpdate: " + query);
		Statement stmt = connection.createStatement();
		stmt.execute(query);
		int update = stmt.getUpdateCount();
		stmt.close();
		return update;
	}
}
