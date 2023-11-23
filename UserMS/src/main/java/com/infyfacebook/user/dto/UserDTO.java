package com.infyfacebook.user.dto;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.infyfacebook.user.utility.AgeConstraint;


public class UserDTO {
	
	private Long userId;
	
	@NotNull(message = "{user.username.notpresent}")
	@Pattern(regexp = "([A-Z][A-Za-z0-9]*)( [A-Z][A-Za-z0-9]*)+", message = "{user.username.invalid}")	
	private String username;
	
	@NotNull(message = "{user.password.notpresent}")
	@Pattern(regexp = ".*[A-Z].*", message = "{user.password.invalid.uppercase}")
	@Pattern(regexp = ".*[a-z].*", message = "{user.password.invalid.lowercase}")
	@Pattern(regexp = ".*[0-9].*", message = "{user.password.invalid.digit}")
	@Pattern(regexp = ".*[^A-Za-z0-9 ].*", message = "{user.password.invalid.special}")
	@Pattern(regexp = ".{8,16}", message = "{user.password.invalid.length}")
	private String password;
	
	@NotNull(message = "{user.email.notpresent}")
	@Email(message = "{user.email.invalid}")
	private String email;
	
	@NotNull(message = "{user.dateOfBirth.notpresent}")
	@Past(message = "{user.dateOfBirth.invalid}")
	@AgeConstraint
	private LocalDate dateOfBirth;
	
	@NotNull(message = "{user.gender.notpresent}")
	private Gender gender;
	
	
	public Long getuserId() {
		return userId;
	}
	public void setuserId(Long userId) {
		this.userId = userId;
	}
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
	
	public LocalDate getdateOfBirth() {
		return dateOfBirth;
	}
	
	public void setdateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, email, gender, userId, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(email, other.email) && gender == other.gender
				&& Objects.equals(userId, other.userId) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + "]";
	}
	
}
