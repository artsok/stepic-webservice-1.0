package ru.stepic.webservice.accounts;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
	
	private final Map<String, UserProfile> loginToProfile = new HashMap<>();;

	private final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	public AccountService() {
		log.info("Создали объект AccountService");
	}
	
	
    public void addNewUser(UserProfile userProfile) {
    	try {
			DbService.getInstance().addUser(userProfile.getLogin(), userProfile.getPass());
		} catch (DBException e) {
			log.error("Exception when add new user", e);
		}
    }

    public UserProfileDataSet getUserByLogin(String login)  {
    	try {
    		log.info(DbService.getInstance().getUser(login).getPassword() + " ddddddddd");
			return DbService.getInstance().getUser(login);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
        log.info("Add user with session: '" + sessionId + "'.");
    }

    public void deleteUser(String login) {
    	loginToProfile.remove(login);
    	log.info("Delete user with login: '" + login + "'.");
    }
    
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
        log.info("Delete session '" + sessionId + "' from stash.");
    }
    
	public Map<String, UserProfile> getLoginToProfile() {
		return loginToProfile;
	}
	
	public Map<String, UserProfile> getSessionIdToProfile() {
		return sessionIdToProfile;
	}
	
}