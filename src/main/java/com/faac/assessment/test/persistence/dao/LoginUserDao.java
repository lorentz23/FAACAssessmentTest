package com.faac.assessment.test.persistence.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.springframework.transaction.annotation.Transactional;

import com.faac.assessment.test.persistence.entity.LoginUser;

public class LoginUserDao extends AbstractDao<LoginUser> {
	
	private static final Logger logger = LogManager.getLogger(LoginUserDao.class);
	
	public LoginUserDao() {
		super(LoginUser.class);
	}

	public LoginUser loginUser(String username, String password) {
		
		if (username == null) {
            return null;
        }
		
        try {
            return em.createNamedQuery(LoginUser.LOGIN_USER, LoginUser.class).
                    setParameter("userName", username).
                    setParameter("password", encryptPassword(password)).
                    getSingleResult();
        } catch (NoResultException | NonUniqueResultException | NoSuchAlgorithmException e) {
            
        	if(e instanceof NoResultException) {
        		logger.error("No user found for username:" + username);
        	}
        	
        	if(e instanceof NonUniqueResultException) {
        		logger.error("There is more than one user with username:" + username);
        	}
        	
        	if(e instanceof NoSuchAlgorithmException) {
        		logger.error("Unable to encrypt password for username:" + username);
        	}
        	
        	return null;
        }
	}
	
	@Transactional
	public LoginUser addUser(String fullName, String userName, String password, String email) {
		
		if (userName == null) {
			logger.error("Unable to add a new user with userName nullable");
			return null;
        }
		
		LoginUser loginUser = getLoginUser(userName);
		
		if(loginUser == null) {
			
			try {
				
				loginUser = new LoginUser();
				loginUser.setFullName(fullName);
				loginUser.setUserName(userName);
				loginUser.setPassword(encryptPassword(password));
				loginUser.setEmail(email);
				
				em.persist(loginUser);
				em.flush();
				
			} catch(Exception e) {
				logger.error("Unable to add user " + userName + " caused by " + e.getMessage());
			}
		}
        
        return loginUser;
		
	}
	
	public LoginUser getLoginUser(String userName) {
		try {
			return em.createNamedQuery(LoginUser.LOGIN_USER_BYUSERNAME, LoginUser.class).setParameter("userName", userName).getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}
	
	/*
	 * Encryption method to store password. MD5 encrypting method is used
	 * 
	 * @param password Password not encrypted
	 * @return the encrypted password that is stored to DB
	 */
	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(password.getBytes(), 0, password.length());
		
		return new BigInteger(1, digest.digest()).toString(16);
	}

	@Override
	public String getFindAllNamedQuery() {
		return LoginUser.LOGIN_USER_FIND_ALL;
	}

}