package com.infyfacebook.posts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infyfacebook.posts.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long>{
	public void deleteAllByPostId(Long postId);
	public Long countByPostId(Long postId);
	public List<Likes> findAllByPostId(Long postId);
	public Optional<Likes> findByLikedByAndPostId(Long likedBy, Long postId);
	public List<Likes> findAllByPostIdOrderByTimestampColumnDesc(Long postId);
}
