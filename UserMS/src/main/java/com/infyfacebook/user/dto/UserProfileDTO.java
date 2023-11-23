package com.infyfacebook.user.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.infyfacebook.user.utility.AgeConstraint;


public class UserProfileDTO {
	
	private Long profileId;
	
	@NotNull(message="{user.profile.id.notpresent}")
	private Long userId;
	private PrivacySetting privacySetting;
	private String username;
	private String profilePicture;
	private String bio;
	
	@NotNull(message = "{user.email.notpresent}")
	@Email(message = "{user.email.invalid}")
	private String email;
	
	@NotNull(message = "{user.dateOfBirth.notpresent}")
	@Past(message = "{user.dateOfBirth.invalid}")
	@AgeConstraint
	private LocalDate dateOfBirth;
	@NotNull(message = "{user.gender.notpresent}")
	private Gender gender;
	
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public PrivacySetting getPrivacySetting() {
		return privacySetting;
	}
	public void setPrivacySetting(PrivacySetting privacySetting) {
		this.privacySetting = privacySetting;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
