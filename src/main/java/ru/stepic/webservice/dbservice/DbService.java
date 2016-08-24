package ru.stepic.webservice.dbservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.dao.OnlineUsersDAO;
import ru.stepic.webservice.dbservice.dao.UserProfileDAO;
import ru.stepic.webservice.dbservice.datasets.OnlineUsersDataSet;
import ru.stepic.webservice.dbservice.datasets.UserProfileDataSet;

/**
 * 
 * @author sokovets-av
 *
 */
public class DbService {

	private final Connection connection;
	private static DbService instance;
	private OnlineUsersDAO onlineUserProfile;
	private UserProfileDAO userProfile;
	
	

	private static final Logger log = LoggerFactory.getLogger(DbService.class);

	private DbService() {
		this.connection = getH2Connection();
		this.onlineUserProfile = new OnlineUsersDAO(connection);
		this.userProfile = new UserProfileDAO(connection);
		try {
			onlineUserProfile.createTable();
			userProfile.createTable();
		} catch (SQLException e) {
			log.error("SQLException in constructor DbService", e);
		}

	}

	public static DbService getInstance() {
		if (instance == null) {
			instance = new DbService();
		}
		return instance;
	}

	/**
	 * Get connection to mysql
	 * 
	 * @return {@link Connection}
	 */
	private Connection getMySqlConnection() {
		try {
			StringBuilder url = new StringBuilder();
			url.append("jdbc:h2:./h2db"). // db type
					append("localhost:"). // host name
					append("3306/"). // port
					append("test?"). // db name
					append("user=root&"). // login
					append("password="); // password
			log.info("URL: " + url + "\n");
			return DriverManager.getConnection(url.toString());
		} catch (SQLException e) {
			log.error("Exception with connect to mysql", e);
		}
		return null;
	}

	/**
	 * Get connection to h2db
	 * 
	 * @return {@link Connection}
	 */
	private Connection getH2Connection() {
		try {
			String url = "jdbc:h2:./h2db";
			String name = "root";
			String pass = "root";

			JdbcDataSource ds = new JdbcDataSource();
			ds.setURL(url);
			ds.setUser(name);
			ds.setPassword(pass);
			return ds.getConnection();
		} catch (SQLException e) {
			log.error("Exception in getH2Connection()", e);
		}
		return null;
	}

	/**
	 * Print connection info
	 */
	public void printConnectInfo() {
		try {
			System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
			System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
			System.out.println("Driver: " + connection.getMetaData().getDriverName());
			System.out.println("Autocommit: " + connection.getAutoCommit());
		} catch (SQLException e) {
			log.error("Exception in printConnectInfo()", e);
		}
	}

	/**
	 * Add login and password to db
	 * 
	 * @param login
	 * @param password
	 * @return return id from 'user' table
	 * @throws DBException
	 */
	public long addUser(String login, String password) throws DBException {
		try {
			connection.setAutoCommit(false); // Отключаем автокоммит -> работаем с транзакциями
			userProfile.insertUser(login, password);
			connection.commit();
			return userProfile.getUserProfileId(login);
		} catch (SQLException e) {
			log.error("Exception in addUser()");
			try {
				connection.rollback();
			} catch (SQLException e2) {
				log.error("Exception in addUser() -  rollback error", e2);
			}
			throw new DBException();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				log.error("Exception in addUser() - finally case");
			}
		}
	}

	/**
	 * Add user, password, sessionid to table 'online_users'
	 * 
	 * @param login {@link String}
	 * @param sessionId {@link String}
	 * @throws DBException
	 */
	public long addOnlineUser(String login, String sessionId) throws DBException {
		try {
			connection.setAutoCommit(false);
			onlineUserProfile.insertUser(login, sessionId);
			connection.commit();
			return onlineUserProfile.getOnlineUserProfileId(login);
		} catch (SQLException e) {
			log.error("Exception in addUser()");
			try {
				connection.rollback();
			} catch (SQLException e2) {
				log.error("Exception in addUser() -  rollback error", e2);
			}
			throw new DBException();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				log.error("Exception in addUser() - finally case");
			}
		}
	}

	/**
	 * Get UserProfileDataSet by login
	 * 
	 * @param login {@link String}
	 * @return {@link UserProfileDataSet}
	 * @throws SQLException
	 */
	public UserProfileDataSet getUser(String login) throws SQLException {
		try {
			return new UserProfileDAO(connection).get(login);
		} catch (DBException e) {
			log.error("Exception in getUser(arg1)", e);
			throw new DBException();
		}
	}
	
	/**
	 * Get OnlineUsersDataSet by login
	 * @param login {@link String}
	 * @return {@link OnlineUsersDataSet}
	 * @throws SQLException
	 */
	public OnlineUsersDataSet getOnlineUser(String login) throws SQLException {
		try {
			return new OnlineUsersDAO(connection).getUserByLogin(login);
		} catch (DBException e) {
			log.error("Exception in getUser(arg1)", e);
			throw new DBException();
		}
	}

}
