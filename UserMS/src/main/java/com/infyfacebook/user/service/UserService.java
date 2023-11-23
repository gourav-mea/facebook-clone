package com.infyfacebook.user.service;

import java.util.List;

import com.infyfacebook.user.dto.ResetPasswordDTO;
import com.infyfacebook.user.dto.UserDTO;
import com.infyfacebook.user.dto.UserLoginDTO;
import com.infyfacebook.user.dto.UserProfileDTO;
import com.infyfacebook.user.exception.InfyFacebookException;

public interface UserService {
	public UserDTO registerUser(UserDTO userDTO) throws InfyFacebookException;
	public void resetPassword(ResetPasswordDTO resetPassowrdDTO) throws InfyFacebookException;
	public UserDTO loginUser(UserLoginDTO userLoginDTO) throws InfyFacebookException;
	public List<UserDTO> getUserBySearchString(String searchString) throws InfyFacebookException;
	public Long getUserId(String username) throws InfyFacebookException;
	public UserDTO getUserByUserId(Long userId) throws InfyFacebookException;
	public void updateUser(UserProfileDTO userProfileDTO) throws InfyFacebookException;
	public Boolean isUserExists(Long userId) throws InfyFacebookException;
}
