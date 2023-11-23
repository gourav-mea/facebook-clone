package com.infyfacebook.user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infyfacebook.user.dto.ResetPasswordDTO;
import com.infyfacebook.user.dto.UserDTO;
import com.infyfacebook.user.dto.UserLoginDTO;
import com.infyfacebook.user.dto.UserProfileDTO;
import com.infyfacebook.user.entity.User;
import com.infyfacebook.user.exception.InfyFacebookException;
import com.infyfacebook.user.repository.UserRepository;


@Service(value = "UserService")
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDTO registerUser(UserDTO userDTO) throws InfyFacebookException{
		Optional<User> optional1 = userRepository.findByUsername(userDTO.getUsername());
		Optional<User> optional2 = userRepository.findByEmail(userDTO.getEmail());
		if(optional1.isPresent() || optional2.isPresent()) {
			throw new InfyFacebookException("Service.USER_ALREADY_PRESENT");
		}
		
		User user = new User();
		user.setDateOfBirth(userDTO.getdateOfBirth());
		user.setEmail(userDTO.getEmail());
		user.setGender(userDTO.getGender());
		user.setPassword(userDTO.getPassword());
		user.setUsername(userDTO.getUsername());
		
		User userReturn = userRepository.save(user);
		userDTO.setuserId(userReturn.getUserId());
		return userDTO;
	}

	@Override
	public void resetPassword(ResetPasswordDTO resetPassowrdDTO) throws InfyFacebookException {
		Optional<User> optional = userRepository.findByUsername(resetPassowrdDTO.getUsername());
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		if(!optional.get().getEmail().equals(resetPassowrdDTO.getEmail())){
			throw new InfyFacebookException("Service.EMAIL_NOT_MATCHED");
		}
		optional.get().setPassword(resetPassowrdDTO.getPassword());
	}

	@Override
	public UserDTO loginUser(UserLoginDTO userLoginDTO) throws InfyFacebookException {
		Optional<User> optional = userRepository.findByUsername(userLoginDTO.getUsername());
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		if(!optional.get().getPassword().equals(userLoginDTO.getPassword())) {
			throw new InfyFacebookException("Service.PASSWORD_NOT_MATCHED");
		}
		else {
			UserDTO userDTO = new UserDTO();
			userDTO.setdateOfBirth(optional.get().getDateOfBirth());
			userDTO.setEmail(optional.get().getEmail());
			userDTO.setGender(optional.get().getGender());
			userDTO.setPassword(optional.get().getPassword());
			userDTO.setuserId(optional.get().getUserId());
			userDTO.setUsername(optional.get().getUsername());
			return userDTO;
		}
	}

	@Override
	public List<UserDTO> getUserBySearchString(String searchString) throws InfyFacebookException {
		List<User> userList = userRepository.findByUsernameContaining(searchString);
		if(userList.isEmpty()) {			
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}else {
			List<UserDTO> userDTOList = new ArrayList<>();
			for(User user: userList) {
				UserDTO userDTO = new UserDTO();
				userDTO.setdateOfBirth(user.getDateOfBirth());
				userDTO.setEmail(user.getEmail());
				userDTO.setGender(user.getGender());
				userDTO.setPassword(user.getPassword());
				userDTO.setuserId(user.getUserId());
				userDTO.setUsername(user.getUsername());
				userDTOList.add(userDTO);
			}
			return userDTOList;
		}
	}

	@Override
	public Long getUserId(String username) throws InfyFacebookException {
		Optional<User> optional = userRepository.findByUsername(username);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		return optional.get().getUserId();
	}

	@Override
	public UserDTO getUserByUserId(Long userId) throws InfyFacebookException {
		Optional<User> optional = userRepository.findById(userId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setdateOfBirth(optional.get().getDateOfBirth());
		userDTO.setEmail(optional.get().getEmail());
		userDTO.setGender(optional.get().getGender());
		userDTO.setPassword(optional.get().getPassword());
		userDTO.setuserId(optional.get().getUserId());
		userDTO.setUsername(optional.get().getUsername());
		return userDTO;
	}

	@Override
	public void updateUser(UserProfileDTO userProfileDTO) throws InfyFacebookException {
		Optional<User> optional = userRepository.findById(userProfileDTO.getUserId());
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.USER_NOT_FOUND");
		}
		optional.get().setDateOfBirth(userProfileDTO.getDateOfBirth());
		optional.get().setGender(userProfileDTO.getGender());
	}

	@Override
	public Boolean isUserExists(Long userId) throws InfyFacebookException {
		Optional<User> optional = userRepository.findById(userId);
		if(!optional.isPresent()) {
			return false;
		}
		return true;
	}
	
}
