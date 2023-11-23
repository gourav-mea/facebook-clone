package com.infyfacebook.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ResetPasswordDTO {
	
	@NotNull(message = "{user.username.notpresent}")
	private String username;
	
	@NotNull(message = "{user.password.notpresent}")
	@Pattern(regexp = ".*[A-Z].*", message = "{user.password.invalid.uppercase}")
	@Pattern(regexp = ".*[a-z].*", message = "{user.password.invalid.lowercase}")
	@Pattern(regexp = ".*[0-9].*", message = "{user.password.invalid.digit}")
	@Pattern(regexp = ".*[^A-Za-z0-9 ].*", message = "{user.password.invalid.special}")
	@Pattern(regexp = ".{8,16}", message = "{user.password.invalid.length}")
	private String password;
	
	@NotNull(message = "{user.email.notpresent}")
	private String email;
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
}
