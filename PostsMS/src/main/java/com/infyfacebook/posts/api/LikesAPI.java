package com.infyfacebook.posts.api;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infyfacebook.posts.dto.LikesDTO;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.service.LikesService;

@CrossOrigin
@RestController
@RequestMapping(value = "likes")
@Validated
public class LikesAPI {
	@Autowired
	private LikesService likesService;
	@Autowired 
	private Environment environment;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> addLike(@Valid @RequestBody LikesDTO likesDTO) throws InfyFacebookException{
		likesService.addLike(likesDTO);
		String message = environment.getProperty("posts.likes.ADDED");
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getcount")
	public ResponseEntity<String> getLikeCount(@RequestParam Long postId) throws InfyFacebookException{
		Long likesCount = likesService.getLikeCount(postId);
		return new ResponseEntity<>(likesCount.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/islikedby")
	public ResponseEntity<Boolean> isLikedBy(@RequestParam Long postId, @RequestParam Long userId) throws InfyFacebookException{
		Boolean isLikedBy = likesService.isLikedBy(postId, userId);
		return new ResponseEntity<>(isLikedBy, HttpStatus.OK);
	}
	
	@PutMapping(value="/unlike")
	public ResponseEntity<String> deleteLike(
			@RequestParam @NotNull(message= "{posts.userid.notpresent}") Long likedBy, 
			@RequestParam @NotNull(message="{posts.postid.notpresent}") Long postId) throws InfyFacebookException{
		likesService.deleteLike(likedBy, postId);
		String message = environment.getProperty("posts.UNLIKED");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping(value="/getlikes")
	public ResponseEntity<List<LikesDTO>> getLikesByPostId(
			@RequestParam @NotNull(message="{posts.postid.notpresent}") Long postId) throws InfyFacebookException{
		List<LikesDTO> likesDTOList = likesService.geLikesByPostId(postId);
		return new ResponseEntity<>(likesDTOList, HttpStatus.OK);
	}
}
