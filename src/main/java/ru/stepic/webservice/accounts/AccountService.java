package ru.stepic.webservice.accounts;

import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.dbservice.DBException;
import ru.stepic.webservice.dbservice.DbService;
import ru.stepic.webservice.entity.OnlineUser;
import ru.stepic.webservice.entity.UserProfile;


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
			DbService.getInstance().addUserProfile(userProfile);
		} catch (DBException e) {
			log.error("Exception in addNewUser(UserProfile userProfile)", e);
		}
    }

    /**
     * Get UserProfileDataSet - class which contain row info from table 'user'
     * @param login {@link String}
     * @return
     */
    public UserProfile getUserByLogin(String login)  {
    	try {
			return DbService.getInstance().getUserProfile(login);
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
	public void addSessionInfo(OnlineUser onlineUser) {
    	try {
			DbService.getInstance().addOnlineUser(onlineUser);
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
		if(Optional.ofNullable(DbService.getInstance().getOnlineUser(login)).isPresent()) {
			return DbService.getInstance().getOnlineUser(login).getSessionid().contains(sessionId);
		}
		return false;
	}

}