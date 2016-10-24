package com.faac.assessment.test.persistence.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.faac.assessment.test.persistence.entity.LoggedUser;
import com.faac.assessment.test.persistence.entity.LoginUser;
import com.faac.assessment.test.persistence.dao.*;

@ContextConfiguration(locations = "classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {
	
	private static final String USERNAME = "lsantini";
	
	private static final String PASSWORD = "lorenzo123";
	@Autowired
	private LoginUserDao loginUserDao;
	
	@Autowired
	private LoggedUserDao loggedUserDao;
	
	@Test
	// @Rollback(true)
	public void loginDaoTest() {
		
		loginUserDao.addUser("Lorenzo Santini", USERNAME, PASSWORD, "glorentz.23@gmail.com");
		LoginUser loginUser = loginUserDao.loginUser(USERNAME, PASSWORD);
		
		Assert.assertNotNull(loginUser);
		Assert.assertEquals(USERNAME, loginUser.getUserName());
	}
	
	@Test
	// @Rollback(true)
	public void loggedUserDaoTest() {
		
		loginUserDao.addUser("Lorenzo Santini", USERNAME, PASSWORD, "glorentz.23@gmail.com");
		LoginUser loginUser = loginUserDao.loginUser(USERNAME, PASSWORD);
		
		loggedUserDao.addLoggedUser(loginUser, "1");
		LoggedUser loggedUser = loggedUserDao.findLoggedUser(USERNAME);
		
		Assert.assertNotNull(loggedUser);
		Assert.assertNotNull(loginUser);
		Assert.assertNotNull(loggedUser.getLoginUser());
		Assert.assertEquals("1", loggedUser.getSessionId());
		Assert.assertEquals(loginUser.getId(), loggedUser.getLoginUser().getId());
	}
	
	@After
	public void removeEntities() {
		removeEntities(loggedUserDao.findAll());
		removeEntities(loginUserDao.findAll());
	}
	
	private <T> void removeEntities(List<T> entityList) {
		
		for(T entity : entityList) {
			if(entity instanceof LoginUser) {
				loginUserDao.removeUser((LoginUser) entity);
			} else if(entity instanceof LoggedUser) {
				loggedUserDao.removeUser((LoggedUser) entity);
			}
		}
	}
}
