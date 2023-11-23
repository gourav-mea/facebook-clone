package com.infyfacebook.user.service;

import java.util.List;

import com.infyfacebook.user.dto.PrivacySetting;
import com.infyfacebook.user.dto.UserDTO;
import com.infyfacebook.user.dto.UserProfileDTO;
import com.infyfacebook.user.exception.InfyFacebookException;

public interface UserProfileService {
	public void registerUserProfile(UserDTO userDTO) throws InfyFacebookException;
	public void saveUserProfile(UserProfileDTO userProfileDTO) throws InfyFacebookException;
	public UserProfileDTO getUserProfileByUserId(Long userId) throws InfyFacebookException;
	public List<UserProfileDTO> getUserProfileBySeachString(String searchString) throws InfyFacebookException;
	public PrivacySetting getPrivacySetting(Long userId) throws InfyFacebookException;
	public UserProfileDTO getFriendProfile(Long friendUserId, Long userId, Boolean isFriend) throws InfyFacebookException;
	
}
