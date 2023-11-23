package com.infyfacebook.posts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infyfacebook.posts.dto.PostsDTO;
import com.infyfacebook.posts.dto.PrivacySetting;
import com.infyfacebook.posts.dto.UserPrivacySetting;
import com.infyfacebook.posts.entity.Posts;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.repository.CommentRepository;
import com.infyfacebook.posts.repository.LikesRepository;
import com.infyfacebook.posts.repository.PostsRepository;


@Service(value="postsService")
@Transactional
public class PostsServiceImpl implements PostsService{
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private LikesRepository likesRepository;
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void savePost(PostsDTO postsDTO) throws InfyFacebookException {
		Posts posts = postsDTO.toEntity();
		postsRepository.save(posts);
	}

	@Override
	public void updatePost(PostsDTO postsDTO) throws InfyFacebookException {
		Optional<Posts> optional = postsRepository.findById(postsDTO.getPostId());
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Serivce.POST_NOT_FOUND");
		}
		optional.get().setPostImage(postsDTO.getPostImage());
		optional.get().setPostText(postsDTO.getPostText());
		optional.get().setPostVideo(postsDTO.getPostVideo());
		optional.get().setPrivacySetting(postsDTO.getPrivacySetting());
	}

	@Override
	public void deletePost(Long postId) throws InfyFacebookException {
		Optional<Posts> optional = postsRepository.findById(postId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Serivce.POST_NOT_FOUND");
		}
		likesRepository.deleteAllByPostId(postId);
		commentRepository.deleteAllByPostId(postId);
		postsRepository.deleteById(postId);
	}

	@Override
	public Long getPostCount(Long userId) throws InfyFacebookException {
		return postsRepository.countByUserId(userId);
	}

	@Override
	public Long getPublicOnlyPostCount(Long userId) throws InfyFacebookException {
		return postsRepository.countByUserIdAndPrivacySetting(userId, PrivacySetting.PUBLIC);
	}

	@Override
	public List<PostsDTO> getFriendPosts(Long friendUserId, Boolean isFriend, UserPrivacySetting userPrivacySetting, Integer pagNum) throws InfyFacebookException {
		Sort sort = Sort.by("timestampColumn").descending();
		Pageable pageable = PageRequest.of(pagNum, 9, sort);
		
		Page<Posts> postsList = Page.empty();
		if(isFriend) {
			postsList = postsRepository.findAllByUserId(friendUserId, pageable);
		}else {
			if(userPrivacySetting.toString().equals("PUBLIC")) {
				postsList = postsRepository.findAllByUserIdAndPrivacySetting(friendUserId, PrivacySetting.PUBLIC, pageable);
			}
		}
		if(!postsList.hasContent()) {
			throw new InfyFacebookException("Service.POST_NOT_FOUND");
		}
		List<PostsDTO> postsDTOList = new ArrayList<>();
		for(Posts post : postsList.getContent()) {
			PostsDTO postsDTO = post.toDTO();
			postsDTOList.add(postsDTO);
		}
		return postsDTOList;
	}

	@Override
	public List<PostsDTO> getUserPosts(Long userId, Integer pagNum) throws InfyFacebookException {
		Sort sort = Sort.by("timestampColumn").descending();
		Pageable pageable = PageRequest.of(pagNum, 9, sort);
		
		Page<Posts> myPostsOptional = postsRepository.findAllByUserId(userId, pageable);
		if (!myPostsOptional.hasContent()) throw new InfyFacebookException("Service.POSTS_NOT_FOUND");
		
		List<PostsDTO> postsDTOList = new ArrayList<>();
		for(Posts post : myPostsOptional.getContent()) {
			PostsDTO postsDTO = post.toDTO();
			postsDTOList.add(postsDTO);
		}
		return postsDTOList;
	}

	@Override
	public List<PostsDTO> getFeedPosts(Long userId, List<Long> friendList, Integer pagNum)
			throws InfyFacebookException {
		Sort sort = Sort.by("timestampColumn").descending();
		Pageable pageable = PageRequest.of(pagNum, 9, sort);
		friendList.add(userId);
		Page<Posts> postsList = postsRepository.findAllByUserIdIn(friendList, pageable);
		if(!postsList.hasContent()) {
			throw new InfyFacebookException("Service.POST_NOT_FOUND");
		}
		List<PostsDTO> postsDTOList = new ArrayList<>();
		for(Posts post : postsList.getContent()) {
			PostsDTO postsDTO = post.toDTO();
			postsDTOList.add(postsDTO);
		}
		return postsDTOList;
	}
	
}
