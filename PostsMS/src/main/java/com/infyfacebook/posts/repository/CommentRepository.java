package com.infyfacebook.posts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infyfacebook.posts.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	void deleteAllByPostId(Long postId);
	Long countByPostId(Long postId);
	Optional<Comment> findByCommentByAndPostId(Long commentBy, Long postId);
	List<Comment> findAllByPostIdOrderByTimestampColumnDesc(Long postId);
}
