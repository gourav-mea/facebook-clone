package com.infyfacebook.posts.service;

import java.util.List;

import com.infyfacebook.posts.dto.LikesDTO;
import com.infyfacebook.posts.exception.InfyFacebookException;


public interface LikesService {
	public void addLike(LikesDTO likesDTO) throws InfyFacebookException;
	public Long getLikeCount(Long postId) throws InfyFacebookException;
	public void deleteLike(Long likedBy, Long postId) throws InfyFacebookException;
	public List<LikesDTO> geLikesByPostId(Long postId) throws InfyFacebookException;
	public Boolean isLikedBy(Long postId, Long userId);;
}
