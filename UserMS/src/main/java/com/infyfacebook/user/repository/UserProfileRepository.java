package com.infyfacebook.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infyfacebook.user.entity.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long>{
	Optional<UserProfile> findByUserId(Long userId);
	List<UserProfile> findByUsernameContaining(String searchString);
}
