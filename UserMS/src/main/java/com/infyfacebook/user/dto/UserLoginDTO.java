package com.infyfacebook.user.dto;

import javax.validation.constraints.NotNull;

public class UserLoginDTO {
	
	@NotNull(message = "{user.username.notpresent}")
	private String username;
	@NotNull(message = "{user.password.notpresent}")
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
