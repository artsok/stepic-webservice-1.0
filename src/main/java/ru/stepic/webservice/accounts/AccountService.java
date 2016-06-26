package ru.stepic.webservice.accounts;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Artem Sokovets
 *
 */
public class AccountService {
	
	private final Map<String, UserProfile> loginToProfile;

	private final Map<String, UserProfile> sessionIdToProfile;
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	public AccountService() {
		loginToProfile = new HashMap<>();
		sessionIdToProfile = new HashMap<>();
	}
	
    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
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