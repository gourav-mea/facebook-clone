package com.infyfacebook.user.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infyfacebook.user.dto.HttpResponseDTO;
import com.infyfacebook.user.dto.PrivacySetting;
import com.infyfacebook.user.dto.UserProfileDTO;
import com.infyfacebook.user.exception.InfyFacebookException;
import com.infyfacebook.user.service.UserProfileService;


@CrossOrigin
@RestController
@RequestMapping(value = "user/profile")
@Validated
public class UserProfileAPI {
	
	@Autowired
	private UserProfileService userProfileService;
	@Autowired 
	private Environment environment;
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(value="/save")
	public ResponseEntity<HttpResponseDTO> saveUserProfile(@Valid @RequestBody UserProfileDTO userProfileDTO) throws InfyFacebookException{
		userProfileService.saveUserProfile(userProfileDTO);
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(environment.getProperty("User.profile_updated"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/view")
	public ResponseEntity<UserProfileDTO> getUserProfileByUserId(@NotNull(message = "{user.profile.id.notpresent}") @RequestParam String userId) throws InfyFacebookException {
		UserProfileDTO userProfileDTO = userProfileService.getUserProfileByUserId(Long.parseLong(userId));
		return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<List<UserProfileDTO>> getUserProfileBySeachString(@RequestParam String searchString) throws InfyFacebookException{
		List<UserProfileDTO> userProfileDTOList = userProfileService.getUserProfileBySeachString(searchString);
		return new ResponseEntity<>(userProfileDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getprivacysetting/{userId}")
	public ResponseEntity<PrivacySetting> getPrivacySetting(@PathVariable @NotNull Long userId) throws InfyFacebookException{
		PrivacySetting privacySetting = userProfileService.getPrivacySetting(userId);
		return new ResponseEntity<>(privacySetting, HttpStatus.OK);
	}
	
	@GetMapping(value = "/viewfriend")
	public ResponseEntity<UserProfileDTO> getFriendProfile(@NotNull(message = "{user.profile.id.notpresent}") Long friendUserId , @RequestParam Long userId) throws InfyFacebookException {
		Boolean isFriend = checkExistingFriend(userId, friendUserId);
		UserProfileDTO userProfileDTO = userProfileService.getFriendProfile(friendUserId, userId, isFriend);
		return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
	}
	
	public Boolean checkExistingFriend(Long userId, Long friendUserId){
		String url = "http://localhost:2000/friendrequest/isfriend?";
		Boolean isFriend = restTemplate.getForObject(url+"userId="+userId+"&"+"friendUserId="+friendUserId, Boolean.class);
		return isFriend;
	}
}
