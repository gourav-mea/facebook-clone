package com.infyfacebook.user.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infyfacebook.user.dto.HttpResponseDTO;
import com.infyfacebook.user.dto.ResetPasswordDTO;
import com.infyfacebook.user.dto.UserDTO;
import com.infyfacebook.user.dto.UserLoginDTO;
import com.infyfacebook.user.exception.InfyFacebookException;
import com.infyfacebook.user.service.UserProfileService;
import com.infyfacebook.user.service.UserService;
import com.infyfacebook.user.utility.ErrorInfo;


@CrossOrigin
@RestController
@RequestMapping(value = "user")
@Validated
public class UserAPI {
	private static final Log LOGGER = LogFactory.getLog(UserAPI.class);
	
	@Autowired
	private UserService userService;
	@Autowired 
	private Environment environment;
	@Autowired
	private UserProfileService userProfileService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) throws InfyFacebookException{
		UserDTO userDTOReturn = userService.registerUser(userDTO);			
		userProfileService.registerUserProfile(userDTOReturn);
		return new ResponseEntity<>(userDTOReturn, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/forgot-password")
	public ResponseEntity<HttpResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) throws InfyFacebookException{
		userService.resetPassword(resetPasswordDTO);
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(environment.getProperty("User.passwordReset.OK"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
//	@CircuitBreaker(name="userService", fallbackMethod="loginFallback")
	@PostMapping(value = "/login")
	public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) throws InfyFacebookException{
		UserDTO userDTO = userService.loginUser(userLoginDTO);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<List<UserDTO>> getUserBySearchString(@RequestParam String searchString) throws InfyFacebookException{
		List<UserDTO> userDTOList = userService.getUserBySearchString(searchString);
		return new ResponseEntity<>(userDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value="/getuserid")
	public ResponseEntity<String> getUserId(@RequestParam String username) throws InfyFacebookException{
		Long userId = userService.getUserId(username);
		String result = ""+userId;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getuser")
	public ResponseEntity<UserDTO> getUserByUserId(@RequestParam @NotNull String userId) throws InfyFacebookException{
		UserDTO userDTO = userService.getUserByUserId(Long.parseLong(userId));
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/isuser/{userId}")
	public ResponseEntity<Boolean> isUserExists(@PathVariable @NotNull String userId) throws InfyFacebookException{
		Boolean isExists = userService.isUserExists(Long.parseLong(userId));
		return new ResponseEntity<>(isExists, HttpStatus.OK);
	}
	
	public ResponseEntity<ErrorInfo> loginFallback(UserLoginDTO userLoginDTO, Throwable throwable) {
		LOGGER.info(userLoginDTO);
		LOGGER.error(throwable.getMessage(), throwable);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		
		if (throwable.getMessage().contains("CircuitBreaker")) {
			errorInfo.setErrorMessage(throwable.getMessage());
		} else {
			errorInfo.setErrorMessage(environment.getProperty(throwable.getMessage()));
		}
		
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
}
