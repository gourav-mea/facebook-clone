package com.infyfacebook.friendrequest.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infyfacebook.friendrequest.dto.FriendRequestDTO;
import com.infyfacebook.friendrequest.dto.HttpResponseDTO;
import com.infyfacebook.friendrequest.dto.UserPrivacySetting;
import com.infyfacebook.friendrequest.exception.InfyFacebookException;
import com.infyfacebook.friendrequest.service.FriendRequestService;


@CrossOrigin
@RestController
@RequestMapping(value = "friendrequest")
@Validated
public class FriendRequestAPI {

	@Autowired 
	private Environment environment;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private FriendRequestService friendRequestService;
	
	@PostMapping(value="/add")
	public ResponseEntity<HttpResponseDTO> addFriend(@Valid @RequestBody FriendRequestDTO friendRequestDTO) throws InfyFacebookException{
		if(isUserExists(friendRequestDTO.getSentTo().toString()) && isUserExists(friendRequestDTO.getSentFrom().toString())) {			
			friendRequestService.addFriend(friendRequestDTO);
		}
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(environment.getProperty("Service.ADD_FRIEND"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping(value="/accept")
	public ResponseEntity<String> acceptFriendRequest(@Valid @RequestBody FriendRequestDTO friendRequestDTO) throws InfyFacebookException{		
		friendRequestService.acceptFriendRequest(friendRequestDTO);
		String message = environment.getProperty("Service.ACCEPT_REQUEST");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@PutMapping(value="/reject")
	public ResponseEntity<String> declineFriendRequest(@Valid @RequestBody FriendRequestDTO friendRequestDTO) throws InfyFacebookException{
		friendRequestService.declineFriendRequest(friendRequestDTO);
		String message = environment.getProperty("Service.REJECT_REQUEST");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@PutMapping(value="/removefriend")
	public ResponseEntity<HttpResponseDTO> removeFriend(@RequestParam Long userId, @RequestParam Long friendUserId) throws InfyFacebookException{
		friendRequestService.removeFriend(userId, friendUserId);
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(environment.getProperty("Service.REMOVED_FRIEND"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/getstatus")
	public ResponseEntity<HttpResponseDTO> getStatus(@RequestParam Long userId, @RequestParam Long friendUserId) throws InfyFacebookException{
		String requestStatus = friendRequestService.getStatus(userId, friendUserId);
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(requestStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/get/sentto")
	public ResponseEntity<List<FriendRequestDTO>> getAllRequestSentTo(@RequestParam Long userId) throws InfyFacebookException{
		List<FriendRequestDTO> friendRequestDTOList = friendRequestService.getAllRequestSentTo(userId);
		return new ResponseEntity<>(friendRequestDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value="/get/sentfrom")
	public ResponseEntity<List<FriendRequestDTO>> getAllRequestSentFrom(@RequestParam Long userId) throws InfyFacebookException{
		List<FriendRequestDTO> friendRequestDTOList = friendRequestService.getAllRequestSentFrom(userId);
		return new ResponseEntity<>(friendRequestDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value="/friendlist")
	public ResponseEntity<List<Long>> getFriendList(@RequestParam Long userId) throws InfyFacebookException{
		List<Long> friendList = new ArrayList<>();
		if(isUserExists(userId.toString()).booleanValue()){			
			friendList = friendRequestService.getFriendList(userId);
		}
		return new ResponseEntity<>(friendList, HttpStatus.OK);
	}
	
	@GetMapping(value="/friendcount")
	public ResponseEntity<String> getFriendCount(@RequestParam Long userId) throws InfyFacebookException{
		Long friendCount = friendRequestService.getFriendCount(userId);
		String result = friendCount+"";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/isfriend")
	public ResponseEntity<Boolean> isFriend(@RequestParam Long userId, @RequestParam Long friendUserId){
		Boolean isExistingFriend = friendRequestService.isFriend(userId, friendUserId);
		return new ResponseEntity<>(isExistingFriend, HttpStatus.OK);
	}
	
	@GetMapping(value="/friend/friendlist")
	public ResponseEntity<List<Long>> getFriendFriendList(@RequestParam Long friendUserId, @RequestParam Long userId) throws InfyFacebookException{
		List<Long> friendList = new ArrayList<>();
		UserPrivacySetting friendPrivacySetting = getPrivacySetting(friendUserId);
		if(isUserExists(friendUserId.toString()).booleanValue()){			
			friendList = friendRequestService.getFriendFriendListList(friendUserId, userId, friendPrivacySetting);
		}
		return new ResponseEntity<>(friendList, HttpStatus.OK);
	}
	
	public Boolean isUserExists(String userId) throws InfyFacebookException {
		String url = "http://localhost:4000/user/isuser/";
		Boolean isExist = restTemplate.getForObject(url+userId, Boolean.class);
		isExist = isExist != null;
		if (!isExist.booleanValue()) throw new InfyFacebookException("FriendRequestAPI.USER_ID_NOT_FOUND");
		return true;
	}
	
	public UserPrivacySetting getPrivacySetting(Long friendUserId){
		String url = "http://localhost:4000/user/profile/getprivacysetting/";
		return restTemplate.getForObject(url+friendUserId, UserPrivacySetting.class);
	}
	
}
