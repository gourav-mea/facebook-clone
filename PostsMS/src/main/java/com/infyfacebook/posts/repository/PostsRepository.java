package com.infyfacebook.posts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infyfacebook.posts.dto.PrivacySetting;
import com.infyfacebook.posts.entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long>{
	Long countByUserIdAndPrivacySetting(Long userId, PrivacySetting privacySetting);
	Long countByUserId(Long userId);
	Page<Posts> findAllByUserId(Long userId, Pageable pageable);
	Page<Posts> findAllByUserIdAndPrivacySetting(Long userId, PrivacySetting privacySetting, Pageable pageable);
	Page<Posts> findAllByUserIdIn(List<Long> friendList, Pageable pageable);
}
