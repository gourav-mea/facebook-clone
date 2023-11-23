package com.infyfacebook.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infyfacebook.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	public List<User> findByUsernameContaining(String searchString);
}
