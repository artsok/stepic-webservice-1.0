package ru.stepic.webservice.dbservice;


import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.entity.OnlineUser;
import ru.stepic.webservice.entity.UserProfile;

/**
 * 
 * @author sokovets-av
 *
 */
public class DbService {

	private static final String PERSISTENT_UNIT_NAME = "stepic";
	private static final Logger log = LoggerFactory.getLogger(DbService.class);
	private static final EntityManagerFactory emf;
	private static final EntityManager em;
	private static final CriteriaBuilder cb;
	
	private static DbService instance;

	static {
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
			em = emf.createEntityManager();
			cb = em.getCriteriaBuilder();
		} catch (Exception e) {
			log.error("Exception in initializer", e);
			throw new ExceptionInInitializerError(e);
		}
	}


	
	public static DbService getInstance() {
		if (instance == null) {
			instance = new DbService();
		}
		return instance;
}
	

	/**
	 * Add user, password to table 'users'
	 * @param userProfile
	 * @return id of add's row
	 * @throws DBException
	 */
	public long addUserProfile(UserProfile userProfile) throws DBException {
		try {
			em.getTransaction().begin();
			em.persist(userProfile);
			em.getTransaction().commit();
			return userProfile.getId();
		} catch (Exception e) {
			log.error("Exception in addUserProfile(UserProfile userProfile)", e);
			 if(em.getTransaction().isActive()) {
					em.getTransaction().rollback();
			 }
			throw new DBException();
		} 
	}

	/**
	 * Add user, sessionid to table 'online_users'
	 * 
	 * @param login {@link String}
	 * @param sessionId {@link String}
	 * @throws DBException
	 */
	public long addOnlineUser(OnlineUser onlineUser) throws DBException {
		try {
			em.getTransaction().begin();
			em.persist(onlineUser);
			em.getTransaction().commit();
			return onlineUser.getId();
		} catch (Exception e) {
			log.error("Exception in addOnlineUser(OnlineUser onlineUser)");
			em.getTransaction().rollback();
			throw new DBException();
		} 
	}

	/**
	 * Get UserProfile by login
	 * 
	 * @param login {@link String}
	 * @return {@link UserProfileDataSet}
	 * @throws SQLException
	 */
	public UserProfile getUserProfile(String login) throws SQLException {
		try {
			CriteriaQuery<UserProfile> query = cb.createQuery(UserProfile.class);
			Root<UserProfile> users = query.from(UserProfile.class);
			query.where(cb.equal(users.get("login"), login));
			TypedQuery<UserProfile> typedQuery = em.createQuery(query);		
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			log.error("Exception in getUserProfile(String login)", e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get OnlineUsersDataSet by login
	 * @param login {@link String}
	 * @return {@link OnlineUsersDataSet}
	 * @throws SQLException
	 */
	public OnlineUser getOnlineUser(String login)  {
		try {
			CriteriaQuery<OnlineUser> query = cb.createQuery(OnlineUser.class);
			Root<OnlineUser> users = query.from(OnlineUser.class);
			query.where(cb.equal(users.get("login"), login));
			TypedQuery<OnlineUser> typedQuery = em.createQuery(query);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			log.error("Exception in getOnlineUser(String login)", e.getMessage());
			return null;
		}
	}

}
