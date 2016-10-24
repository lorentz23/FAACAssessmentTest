package com.faac.assessment.test.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.faac.assessment.test.persistence.entity.LoggedUser.*;

/*
 * Entity class that persist the logged_users table that track all the users logged to the faac system
 */
@Entity
@Table(name="logged_user")
@NamedQueries ({
	@NamedQuery(name=LOGGED_USER_BY_USERNAME, query="SELECT lu FROM LoggedUser lu WHERE lu.loginUser.userName=:userName"),
	@NamedQuery(name=LOGGED_USER_FIND_ALL, query="SELECT lu FROM LoggedUser lu")
})
public class LoggedUser implements Serializable {
		
	private static final long serialVersionUID = 9148542174019652783L;
	
	public static final String LOGGED_USER_BY_USERNAME = "logged.user.byUserName";
	public static final String LOGGED_USER_FIND_ALL = "logged.user.findAll";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="session_id", nullable=false)
	private String sessionId;
	
	@OneToOne
	@JoinColumn(name="login_user_id", nullable=false)
	private LoginUser loginUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	@Override
	public String toString() {
		return "LoggedUser [id=" + id + ", sessionId=" + sessionId + ", loginUser=" + loginUser + "]";
	}
}
