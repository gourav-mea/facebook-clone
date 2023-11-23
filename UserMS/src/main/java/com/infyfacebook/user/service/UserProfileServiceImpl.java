package com.infyfacebook.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infyfacebook.user.dto.PrivacySetting;
import com.infyfacebook.user.dto.UserDTO;
import com.infyfacebook.user.dto.UserProfileDTO;
import com.infyfacebook.user.entity.UserProfile;
import com.infyfacebook.user.exception.InfyFacebookException;
import com.infyfacebook.user.repository.UserProfileRepository;


@Service(value = "userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserService userService;

	@Override
	public void registerUserProfile(UserDTO userDTO) throws InfyFacebookException {
		Optional<UserProfile> optional = userProfileRepository.findByUserId(userDTO.getuserId());
		if (optional.isPresent()) {
			throw new InfyFacebookException("Service.PROFILE_ALREADY_PRESENT");
		}
		UserProfile userProfile = new UserProfile();
		userProfile.setDateOfBirth(userDTO.getdateOfBirth());
		userProfile.setEmail(userDTO.getEmail());
		userProfile.setGender(userDTO.getGender());
		userProfile.setUserId(userDTO.getuserId());
		userProfile.setUsername(userDTO.getUsername());
		userProfileRepository.save(userProfile);
	}

	@Override
	public void saveUserProfile(UserProfileDTO userProfileDTO) throws InfyFacebookException {
		userService.updateUser(userProfileDTO);
		Optional<UserProfile> optional = userProfileRepository.findByUserId(userProfileDTO.getUserId());
		if (!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}

		if (userProfileDTO.getBio() != null) {
			optional.get().setBio(userProfileDTO.getBio());
		}
		if (userProfileDTO.getPrivacySetting() != null) {
			optional.get().setPrivacySetting(userProfileDTO.getPrivacySetting());
		}
		if (userProfileDTO.getProfilePicture() != null) {
			optional.get().setProfilePicture(userProfileDTO.getProfilePicture());
		}
		optional.get().setDateOfBirth(userProfileDTO.getDateOfBirth());
		optional.get().setGender(userProfileDTO.getGender());
	}

	@Override
	public UserProfileDTO getUserProfileByUserId(Long userId) throws InfyFacebookException {
		Optional<UserProfile> optional = userProfileRepository.findByUserId(userId);
		if (!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}

		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.setBio(optional.get().getBio());
		userProfileDTO.setDateOfBirth(optional.get().getDateOfBirth());
		userProfileDTO.setEmail(optional.get().getEmail());
		userProfileDTO.setGender(optional.get().getGender());
		userProfileDTO.setPrivacySetting(optional.get().getPrivacySetting());
		userProfileDTO.setProfileId(optional.get().getProfileId());
		userProfileDTO.setProfilePicture(optional.get().getProfilePicture());
		userProfileDTO.setUserId(optional.get().getUserId());
		userProfileDTO.setUsername(optional.get().getUsername());

		return userProfileDTO;
	}

	@Override
	public List<UserProfileDTO> getUserProfileBySeachString(String searchString) throws InfyFacebookException {
		List<UserProfile> userProfileList = userProfileRepository.findByUsernameContaining(searchString);
		if (userProfileList.isEmpty()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		List<UserProfileDTO> userProfileDTOList = new ArrayList<>();
		for (UserProfile userProfile : userProfileList) {
			UserProfileDTO userProfileDTO = new UserProfileDTO();

			userProfileDTO.setBio(userProfile.getBio());
			userProfileDTO.setDateOfBirth(userProfile.getDateOfBirth());
			userProfileDTO.setEmail(userProfile.getEmail());
			userProfileDTO.setGender(userProfile.getGender());
			userProfileDTO.setPrivacySetting(userProfile.getPrivacySetting());
			userProfileDTO.setProfileId(userProfile.getProfileId());
			userProfileDTO.setProfilePicture(userProfile.getProfilePicture());
			userProfileDTO.setUserId(userProfile.getUserId());
			userProfileDTO.setUsername(userProfile.getUsername());

			userProfileDTOList.add(userProfileDTO);
		}

		return userProfileDTOList;
	}

	@Override
	public PrivacySetting getPrivacySetting(Long userId) throws InfyFacebookException {
		Optional<UserProfile> optional = userProfileRepository.findById(userId);
		if (!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		return optional.get().getPrivacySetting();
	}

	@Override
	public UserProfileDTO getFriendProfile(Long friendUserId, Long userId, Boolean isFriend)
			throws InfyFacebookException {
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		PrivacySetting privacySetting = getPrivacySetting(friendUserId);
		if (privacySetting == null) {
			privacySetting = PrivacySetting.valueOf("PUBLIC");
		}
		if (isFriend) {
			if (privacySetting.toString().equals("PRIVATE")) {
				throw new InfyFacebookException("Service.ACCESS_DENIED");
			}
			userProfileDTO = getUserProfileByUserId(friendUserId);
			return userProfileDTO;
		} else {
			if (privacySetting.toString().equals("PUBLIC")) {
				userProfileDTO = getUserProfileByUserId(friendUserId);
				return userProfileDTO;
			} else {
				throw new InfyFacebookException("Service.ACCESS_DENIED");
			}
		}
	}

}
