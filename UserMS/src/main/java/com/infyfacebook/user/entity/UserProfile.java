package com.infyfacebook.user.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.infyfacebook.user.dto.Gender;
import com.infyfacebook.user.dto.PrivacySetting;

@Entity
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;
	
	private Long userId;
	
	private String username;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String profilePicture;
	private String bio;
	private String email;
	private LocalDate dateOfBirth;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private PrivacySetting privacySetting = PrivacySetting.PUBLIC;
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public PrivacySetting getPrivacySetting() {
		return privacySetting;
	}
	public void setPrivacySetting(PrivacySetting privacySetting) {
		this.privacySetting = privacySetting;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setprofileId(Long profileId) {
		this.profileId = profileId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "UserProfile [profileId=" + profileId + ", userId=" + userId + ", username=" + username + ", profilePicture="
				+ profilePicture + ", bio=" + bio + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender.toString() + "]";
	}
	
	
	
}
