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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infyfacebook.posts.dto.CommentDTO;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.service.CommentService;


@CrossOrigin
@RestController
@RequestMapping(value = "comment")
@Validated
public class CommentAPI {
	@Autowired
	private CommentService commentService;
	@Autowired 
	private Environment environment;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> addComment(@Valid @RequestBody CommentDTO commentDTO) throws InfyFacebookException{
		commentService.addComment(commentDTO);
		String message = environment.getProperty("posts.comment.ADDED");
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/getcount")
	public ResponseEntity<String> getCommentCount(@RequestParam Long postId) throws InfyFacebookException{
		Long commentCount = commentService.getCommentCount(postId);
		return new ResponseEntity<>(commentCount.toString(), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete")
	public ResponseEntity<String> deleteComment(
			@RequestParam @NotNull(message= "{posts.userid.notpresent}") Long commentBy, 
			@RequestParam @NotNull(message="{posts.postid.notpresent}") Long postId) throws InfyFacebookException{
		commentService.deleteComment(commentBy, postId);
		String message = environment.getProperty("posts.comment.deleted");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping(value="/getcomments")
	public ResponseEntity<List<CommentDTO>> getCommentByPostId(
			@RequestParam @NotNull(message="{posts.postid.notpresent}") Long postId) throws InfyFacebookException{
		List<CommentDTO> commentDTOList = commentService.geCommentByPostId(postId);
		return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
	}
}

