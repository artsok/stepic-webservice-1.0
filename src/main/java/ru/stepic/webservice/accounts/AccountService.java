package ru.stepic.webservice.accounts;

import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.DBException;
import ru.stepic.webservice.dbservice.DbService;
import ru.stepic.webservice.dbservice.datasets.UserProfileDataSet;

/**
 * 
 * @author Artem Sokovets
 *
 */
public class AccountService {
	
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	/**
	 * Add new user 
	 * @param userProfile {@link UserProfile}
	 */
    public void addNewUser(UserProfile userProfile) {
    	try {
			DbService.getInstance().addUser(userProfile.getLogin(), userProfile.getPass());
		} catch (DBException e) {
			log.error("Exception in addNewUser()", e);
		}
    }

    /**
     * Get UserProfileDataSet - class which contain row info from table 'user'
     * @param login {@link String}
     * @return
     */
    public UserProfileDataSet getUserByLogin(String login)  {
    	try {
			return DbService.getInstance().getUser(login);
		} catch (SQLException e) {
			log.error("Exception in getUserByLogin()", e);
			return null;
		}
    }

    /**
     * Add info about session to table online_user
     * @param userProfile {@link UserProfile}
     * @param sessionId {@link String}
     */
	public void addSessionInfo(UserProfile userProfile, String sessionId) {
    	try {
			DbService.getInstance().addOnlineUser(userProfile.getLogin(), sessionId);
		} catch (DBException e) {
			log.error("Exception in addSessionInfo()", e);
		}
	}

	/**
	 * Check is user online
	 * @param login {@link String}
	 * @param sessionId {@link String}
	 * @return true/false
	 */
	public boolean isUserOnline(String login, String sessionId) {
		try {
			if(Optional.ofNullable(DbService.getInstance().getOnlineUser(login)).isPresent()) {
				return DbService.getInstance().getOnlineUser(login).getSessionId().contains(sessionId);
			}
		} catch (SQLException e) {
			log.error("Exception in isUserOnline()", e);
		}
		return false;
	}

}