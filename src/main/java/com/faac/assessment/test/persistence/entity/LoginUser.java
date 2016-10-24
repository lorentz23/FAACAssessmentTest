package com.faac.assessment.test.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import static com.faac.assessment.test.persistence.entity.LoginUser.*;

/*
 * Entity class that persist the login_user table that the user that access to the system
 */
@Entity
@Table(name="login_user")
@NamedQueries({
	@NamedQuery(name=LOGIN_USER, query="SELECT lu FROM LoginUser lu WHERE lu.userName=:userName AND lu.password=:password"),
	@NamedQuery(name=LOGIN_USER_BYUSERNAME, query="SELECT lu FROM LoginUser lu WHERE lu.userName=:userName"),
	@NamedQuery(name=LOGIN_USER_FIND_ALL, query="SELECT lu FROM LoginUser lu")
})
public class LoginUser implements Serializable {
	
	private static final long serialVersionUID = 3323530315235421802L;
	
	public static final String LOGIN_USER = "login.user";
	public static final String LOGIN_USER_BYUSERNAME = "login.user.byUserName";
	public static final String LOGIN_USER_FIND_ALL = "login.user.findAll";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="username", nullable=false)
	private String userName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password", nullable=false)
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	
}