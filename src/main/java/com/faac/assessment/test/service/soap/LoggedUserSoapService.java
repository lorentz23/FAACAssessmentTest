package com.faac.assessment.test.service.soap;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.faac.assessment.test.persistence.dao.LoggedUserDao;
import com.faac.assessment.test.persistence.entity.LoggedUser;

@WebService(name="LoggedUsersSoap")
public class LoggedUserSoapService {
	
	private LoggedUserDao loggedUserDao;
	
	/*
	 * Method that check how many users are logged inside the login application
	 *
	 * @return: Lazy array String of logged user name 
	 */
	@WebMethod(operationName="getLoggedUsers")
	public String[] getLoggedUsers() {
		
		List<String> loggedUserCache = new ArrayList<String>();
		
		for(LoggedUser loggedUser : loggedUserDao.findLoggedUsers()) {
			loggedUserCache.add(loggedUser.getLoginUser().getUserName());
		}
		
		return loggedUserCache.toArray(new String[]{});
	}

	public LoggedUserDao getLoggedUserDao() {
		return loggedUserDao;
	}

	public void setLoggedUserDao(LoggedUserDao loggedUserDao) {
		this.loggedUserDao = loggedUserDao;
	}
	
}