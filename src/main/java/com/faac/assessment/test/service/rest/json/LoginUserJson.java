package com.faac.assessment.test.service.rest.json;

import java.io.Serializable;

public class LoginUserJson implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fullname;
	private String username;
	private String email;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
