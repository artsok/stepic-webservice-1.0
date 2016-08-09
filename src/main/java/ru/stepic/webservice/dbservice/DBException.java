package ru.stepic.webservice.dbservice;

import java.sql.SQLException;

public class DBException extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException() {
		super();
	}
	
	public DBException(String name) {
		super(name);
	}
}
