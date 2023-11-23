package com.infyfacebook.posts.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infyfacebook.posts.dto.HttpResponseDTO;
import com.infyfacebook.posts.dto.PostsDTO;
import com.infyfacebook.posts.dto.UserPrivacySetting;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.service.PostsService;

@CrossOrigin
@RestController
@RequestMapping(value = "posts")
@Validated
public class PostsAPI {
	
	@Autowired
	private PostsService postsService;
	@Autowired 
	private Environment environment;
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(value="/save")
	public ResponseEntity<HttpResponseDTO> savePost(@Valid @RequestBody PostsDTO postsDTO) throws InfyFacebookException {
		postsService.savePost(postsDTO);
		HttpResponseDTO response = new HttpResponseDTO();
		response.setMessage(environment.getProperty("PostAPI.POST_SAVE_SUCCESSFUL"));
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/update")
	public ResponseEntity<String> updatePost(@Valid @RequestBody PostsDTO postsDTO) throws InfyFacebookException {
		postsService.updatePost(postsDTO);
		String message = environment.getProperty("PostAPI.POST_UPDATE_SUCCESSFUL");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deletePost(@RequestParam String postId) throws InfyFacebookException{
		postsService.deletePost(Long.parseLong(postId));
		String message = environment.getProperty("PostAPI.POST_DELETE_SUCCESSFUL");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping(value = "/count/all")
	public ResponseEntity<String> getPostCount(@NotNull(message = "{posts.userid.notpresent}") @RequestParam Long userId) throws InfyFacebookException {
		Long postCount = postsService.getPostCount(userId);
		return new ResponseEntity<>(postCount.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/count/publiconly")
	public ResponseEntity<String> getPublicOnlyPostCount(@NotNull(message = "{posts.userid.notpresent}") @RequestParam Long userId) throws InfyFacebookException {
		Long postCount = postsService.getPublicOnlyPostCount(userId);
		return new ResponseEntity<>(postCount.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/friendposts/{friendUserId}/{pageNum}")
	public ResponseEntity<List<PostsDTO>> getFriendPosts(@NotNull(message = "{posts.userid.notpresent}") @RequestParam Long userId,
			@PathVariable(value = "friendUserId") @NotNull(message = "{posts.friendUserId.notpresent}") Long friendUserId,
			@PathVariable(value = "pageNum") @NotNull(message = "{posts.pagenum.notpresent}") @Min(value = 0, message = "{posts.pagenum.invalid}") Integer pageNum)
			throws InfyFacebookException {
		Boolean isFriend = checkExistingFriend(userId, friendUserId);
		UserPrivacySetting 	userPrivacySetting = getPrivacySetting(friendUserId);
		List<PostsDTO> postsDTOList= postsService.getFriendPosts(friendUserId, isFriend, userPrivacySetting, pageNum);
		return new ResponseEntity<>(postsDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/myposts")
	public ResponseEntity<List<PostsDTO>> getUserPosts(@NotNull(message = "{posts.userid.notpresent}") @RequestParam Long userId,
			@RequestParam @NotNull(message = "{posts.pagenum.notpresent}") @Min(value = 0, message = "{posts.pagenum.invalid}") Integer pageNum)
			throws InfyFacebookException {
		List<PostsDTO> postDTOList = postsService.getUserPosts(userId, pageNum);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/feed")
	public ResponseEntity<List<PostsDTO>> getFeedPosts(@NotNull(message = "{posts.userid.notpresent}") @RequestParam Long userId,
			@RequestParam @NotNull(message = "{posts.pagenum.notpresent}") @Min(value = 0, message = "{posts.pagenum.invalid}") Integer pageNum)
			throws InfyFacebookException {
		List<Long> friendList = getFriendList(userId);
		List<PostsDTO> postDTOList = postsService.getFeedPosts(userId, friendList, pageNum);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}
	
	public Boolean checkExistingFriend(Long userId, Long friendUserId){
		String url = "http://localhost:2000/friendrequest/isfriend?";
		Boolean isFriend = restTemplate.getForObject(url+"userId="+userId+"&"+"friendUserId="+friendUserId, Boolean.class);
		return isFriend;
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getFriendList(Long userId){
		String url = "http://localhost:2000/friendrequest/friendlist?";
		  List<Integer> friendList = restTemplate.getForObject(url + "userId=" + userId, ArrayList.class);
		  List<Long> longFriendList = new ArrayList<>();
		  for (Integer integerFriendId : friendList) {
		    longFriendList.add(integerFriendId.longValue());
		  }
		  return longFriendList;
	}
	
	public UserPrivacySetting getPrivacySetting(Long friendUserId){
		String url = "http://localhost:4000/user/profile/getprivacysetting/";
		UserPrivacySetting userPrivacySetting = restTemplate.getForObject(url+friendUserId, UserPrivacySetting.class);
		return userPrivacySetting;
	}
}
