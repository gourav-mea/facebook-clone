package com.infyfacebook.posts.service;

import java.util.List;

import com.infyfacebook.posts.dto.CommentDTO;
import com.infyfacebook.posts.exception.InfyFacebookException;


public interface CommentService {
	public void addComment(CommentDTO commentDTO) throws InfyFacebookException;
	public Long getCommentCount(Long postId) throws InfyFacebookException;
	public void deleteComment(Long commentBy, Long postId) throws InfyFacebookException;
	public List<CommentDTO> geCommentByPostId(Long postId) throws InfyFacebookException;
}
