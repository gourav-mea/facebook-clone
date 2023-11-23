package com.infyfacebook.posts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infyfacebook.posts.dto.CommentDTO;
import com.infyfacebook.posts.entity.Comment;
import com.infyfacebook.posts.entity.Posts;
import com.infyfacebook.posts.exception.InfyFacebookException;
import com.infyfacebook.posts.repository.CommentRepository;
import com.infyfacebook.posts.repository.PostsRepository;

@Service(value="commentService")
@Transactional
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostsRepository postsRepository;
	
	@Override
	public void addComment(CommentDTO commentDTO) throws InfyFacebookException {
		Comment comment = commentDTO.toEntity();
		commentRepository.save(comment);
	}

	@Override
	public Long getCommentCount(Long postId) throws InfyFacebookException {
		Optional<Posts> optional = postsRepository.findById(postId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.POST_NOT_FOUND");
		}
		Long commentCount = commentRepository.countByPostId(postId);
		if(commentCount == null) {
			commentCount = 0L;
		}
		return commentCount;
	}

	@Override
	public void deleteComment(Long commentBy, Long postId) throws InfyFacebookException {
		Optional<Comment> optional = commentRepository.findByCommentByAndPostId(commentBy, postId);
		if(!optional.isPresent()) {
			throw new InfyFacebookException("Service.COMMENT_NOT_FOUND");
		}
		commentRepository.delete(optional.get());
	}

	@Override
	public List<CommentDTO> geCommentByPostId(Long postId) throws InfyFacebookException {
		List<Comment> commentList = commentRepository.findAllByPostIdOrderByTimestampColumnDesc(postId);
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for(Comment comment : commentList) {
			CommentDTO commentDTO = comment.toDTO();
			commentDTOList.add(commentDTO);
		}
		return commentDTOList;
	}
}
