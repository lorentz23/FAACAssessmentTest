package com.faac.assessment.test.persistence.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import com.faac.assessment.test.persistence.entity.LoggedUser;
import com.faac.assessment.test.persistence.entity.LoginUser;

public class LoggedUserDao extends AbstractDao<LoggedUser> {

	public LoggedUserDao() {
		super(LoggedUser.class);
	}
	
	public LoggedUser findLoggedUser(String userName) {
		
		if(userName == null) {
			return null;
		}
		
		try {
			return em.createNamedQuery(LoggedUser.LOGGED_USER_BY_USERNAME, LoggedUser.class).
				   setParameter("userName", userName).
				   getSingleResult();
		} catch(EntityNotFoundException | NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public void addLoggedUser(LoginUser loginUser, String sessionId) {

		LoggedUser loggedUser = findLoggedUser(loginUser.getUserName());
		
		if(loggedUser == null) {
			loggedUser = new LoggedUser();
			loggedUser.setSessionId(sessionId);
			loggedUser.setLoginUser(loginUser);
			
			if(loginUser != null) {
				try {
					em.persist(loggedUser);
					em.flush();
				} catch(Exception e ) {
					logger.error("Unable to persist logged User " + loginUser + " - " + sessionId + " due to " + e.getMessage());
				}
			}
		}
	}
	
	public void logoutSession(String userName) {
		
		try {
			
			removeUser(findLoggedUser(userName));
			
		} catch(Exception e) {
			logger.error("Unable to logout user due to " + e.getMessage());
		}
		
	}
	
	public List<LoggedUser> findLoggedUsers() {

		try {
			return em.createNamedQuery("logged.users.findAll", LoggedUser.class).getResultList();
		} catch(NoResultException e) {
			logger.error("No logged users to the application");
		}
		
		return null;
	}

	@Override
	public String getFindAllNamedQuery() {
		return LoggedUser.LOGGED_USER_FIND_ALL;
	}
	
	
}