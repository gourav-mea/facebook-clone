package com.infyfacebook.posts.service;

import java.util.List;

import com.infyfacebook.posts.dto.PostsDTO;
import com.infyfacebook.posts.dto.UserPrivacySetting;
import com.infyfacebook.posts.exception.InfyFacebookException;

public interface PostsService {
	public void savePost(PostsDTO postsDTO) throws InfyFacebookException;
	public void updatePost(PostsDTO postsDTO) throws InfyFacebookException;
	public void deletePost(Long postId) throws InfyFacebookException;
	public Long getPostCount(Long userId) throws InfyFacebookException;
	public Long getPublicOnlyPostCount(Long userId) throws InfyFacebookException;
	public List<PostsDTO> getFriendPosts(Long friendUserId, Boolean isFriend, UserPrivacySetting userPrivacySetting, Integer pagNum) throws InfyFacebookException;
	public List<PostsDTO> getUserPosts(Long userId, Integer pagNum) throws InfyFacebookException;
	public List<PostsDTO> getFeedPosts(Long userId, List<Long> friendList,Integer pagNum) throws InfyFacebookException;
}
