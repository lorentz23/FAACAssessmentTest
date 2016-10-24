package com.faac.assessment.test.service.rest;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.faac.assessment.test.persistence.dao.LoggedUserDao;
import com.faac.assessment.test.persistence.dao.LoginUserDao;
import com.faac.assessment.test.persistence.entity.LoggedUser;
import com.faac.assessment.test.persistence.entity.LoginUser;
import com.faac.assessment.test.service.rest.json.LoginUserJson;

/*
 * LoginUserRest service to do the login operation
 */
@Path("/")
public class LoginUserRestService {
	
	private static Logger logger = Logger.getLogger(LoginUserRestService.class);
	
	private LoginUserDao loginUserDao;
	private LoggedUserDao loggedUserDao;

	/*
	 * loginUser get as input username and password and return the LoginUserJson
	 * 
	 * @param userName Login username to access system
	 * @param password Password to access system
	 * @return LoginUserJson of the user
	 */
	@Path("login")
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response loginUser(@Context HttpServletRequest request,
							  @FormParam(value = "userName") String userName,
							  @FormParam(value = "password") String password) {
		
		LoginUser loginUser = loggedUserDao.findLoggedUser(userName).getLoginUser();
		
		if(loginUser == null) {
			
			LoginUser user = loginUserDao.loginUser(userName, password);
				
			if(user != null) {
				LoggedUser loggedUser = new LoggedUser();
				loggedUser.setLoginUser(loginUser);
				loggedUser.setSessionId(request.getSession().getId());
				
				return Response.ok().entity(buildJson(user, new LoginUserJson())).build();
			}
			
			return Response.serverError().entity("Unable to login " + userName + " is not registred").build();
		
		} else {
			return Response.serverError().entity("Unable to authenticate user, it is still logged").build();
		}
	}
	
	/*
	 * Register method to add a LoginUser to the application
	 * 
	 * @param fullName Name and Surname of the LoginUser
	 * @param userName Username that should be used to the login
	 * @param password LoginUser password will be encripted
	 * @param email LoginUser Mail address
	 * @return LoginUser Json of the registred user
	 */
	@POST
	@Path("register")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response registerUser(@FormParam(value = "fullName") String fullName,
							  @FormParam(value = "userName") String userName,
							  @FormParam(value = "password") String password,
							  @FormParam(value = "email") String email) {
		
		LoginUser loginUser = loginUserDao.addUser(fullName, userName, password, email);
			
		if(loginUser != null) {
			return Response.ok().entity(buildJson(loginUser, new LoginUserJson())).build();
		}
		
		return Response.serverError().entity("Unable to register " + userName).build();
	}
	
	/*
	 * Logout method to remove a LoginUser to the actual session
	 * 
	 * @param userName Username that should be used to the login
	 * @return LoginUser Json of the registred user
	 */
	@DELETE
	@Path("logout")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response logoutUser(@QueryParam(value="userName") String userName) {
		
		LoggedUser loggedUser = loggedUserDao.findLoggedUser(userName);
		
		if(loggedUser != null) {
			loggedUserDao.logoutSession(userName);
			return Response.ok().entity(userName + " logout successfully").build();
		} else {
			return Response.serverError().entity("Unable to logout " + userName).build();
		}
	}
	
	/*
	 * Generic method that convert entity Class of the LoginUser to LoginUserJson
	 * 
	 * @param entity entity class that should be converted
	 * @param json return instance
	 * @return LoginUserJson to the view component
	 */
	private <T, K> K buildJson(T entity, K json) {
		
		Field entityField = null;
		
		for(Field jsonField : json.getClass().getDeclaredFields()) {
			
			try {
				entityField = entity.getClass().getDeclaredField(jsonField.getName());
				
				if(entityField != null) {
					jsonField.set(json, entityField);
				}
			} catch(Exception e) {
				logger.error("Error on converting entity to Json due to " + e.getMessage(), e);
			}
		}
		
		return json;
	}
}