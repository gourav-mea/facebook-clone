package com.infyfacebook.posts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infyfacebook.posts.dto.LikesDTO;
import com.infyfacebook.posts.entity.Likes;
import com.infyfacebook.posts.entity.Posts;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.repository.LikesRepository;
import com.infyfacebook.posts.repository.PostsRepository;


@Service(value="likesService")
@Transactional
public class LikesServiceImpl implements LikesService{
	@Autowired
	private LikesRepository likesRepository;
	@Autowired
	private PostsRepository postsRepository;

	@Override
	public void addLike(LikesDTO likesDTO) throws InfyFacebookException {
		Optional<Likes> optional = likesRepository.findByLikedByAndPostId(likesDTO.getLikedBy(), likesDTO.getPostId());
		if(optional.isPresent()) {
			throw new InfyFacebookException("Service.ALREADY_LIKED_POST");
		}
		Likes likes = likesDTO.toEntity();
		likesRepository.save(likes);
	}

	@Override
	public Long getLikeCount(Long postId) throws InfyFacebookException {
		Optional<Posts> optional = postsRepository.findById(postId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.POST_NOT_FOUND");
		}
		Long likeCount = likesRepository.countByPostId(postId);
		if(likeCount == null) {
			likeCount = 0L;
		}
		return likeCount;
	}

	@Override
	public void deleteLike(Long likedBy, Long postId) throws InfyFacebookException {
		Optional<Likes> optional = likesRepository.findByLikedByAndPostId(likedBy, postId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.LIKE_NOT_FOUND");
		}
		likesRepository.delete(optional.get());
	}

	@Override
	public List<LikesDTO> geLikesByPostId(Long postId) throws InfyFacebookException {
		List<Likes> likesList = likesRepository.findAllByPostIdOrderByTimestampColumnDesc(postId);
		List<LikesDTO> likesDTOList = new ArrayList<>();
		for(Likes like : likesList) {
			LikesDTO likesDTO = like.toDTO();
			likesDTOList.add(likesDTO);
		}
		return likesDTOList;
	}

	@Override
	public Boolean isLikedBy(Long postId, Long userId) {
		Optional<Likes> optional = likesRepository.findByLikedByAndPostId(userId, postId);
		if(optional.isPresent()) {
			return true;
		}
		return false;
	}
}
