package ru.stepic.webservice.dbservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ThrowableProxyVO;
import ru.stepic.webservice.dbservice.dao.UsersDAO;
import ru.stepic.webservice.dbservice.datasets.UsersDataSet;
import ru.stepic.webservice.servlets.Frontend;

/**
 * 
 * @author sokovets-av
 *
 */
public class DbService {

	private final Connection connection;
	
	private static DbService instance; 
	
	private static final Logger log = LoggerFactory.getLogger(DbService.class);
	
	private DbService() {
		this.connection = getH2Connection();
	}
	
	public static DbService getInstance() {
		if(instance == null) {
			instance  = new DbService();
		}
		return instance;
	}

	/**
	 * Get connection to mysql
	 * @return {@link Connection}
	 */
	private Connection getMySqlConnection() {
		try {
			StringBuilder url = new StringBuilder();
	        url.
	        	append("jdbc:h2:./h2db").       //db type
	        	append("localhost:").          //host name
	        	append("3306/").               //port
	        	append("test?").              //db name
	        	append("user=root&").           //login
	        	append("password=");           //password
	        log.info("URL: " + url + "\n");       
			return DriverManager.getConnection(url.toString());
		} catch (SQLException e) {
			log.error("Exception with connect to mysql", e);
		}
		return null;
	}
	
	/**
	 * Get connection to h2db
	 * @return {@link Connection}
	 */
	private Connection getH2Connection() {
		try {
			String url = "jdbc:h2:./h2db";
			String name = "root";
			String pass = "root";
	        log.info("H2DB URL: " + url + "\n");       
			return DriverManager.getConnection(url, name, pass);
		} catch (Exception e) {
			log.error("Exception with connect to h2db", e);
		}
		return null;
	}
	
	
	/**
	 * Print connect info
	 */
    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
        	log.error("Exception in print method", e);
        }
    }
    
    public void cleanUp() throws DBException {
    	UsersDAO dao = new UsersDAO(connection);
    	try {
			dao.dropTable();
		} catch (SQLException e) {
			throw new DBException();
		}
    	
    }
	
	public long addUser(String name) throws DBException {
		try {
			connection.setAutoCommit(false); //turn off autocommit -> will work with transactions 
			UsersDAO user = new UsersDAO(connection);
			user.createTable();
			user.insertUser(name);
			connection.commit();
			return user.getUserId(name);
		} catch (SQLException e) { 
			try {
				connection.rollback();
			} catch (SQLException e2) {
				
			}
			throw new DBException();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}

		
	}
	
	public UsersDataSet getUser(long id) throws SQLException {
		try {
			return new UsersDAO(connection).get(id);
		} catch (DBException e) {
			throw new DBException();
		}
	}
	
}
