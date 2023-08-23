package com.sermaluc.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sermaluc.service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findOneByEmail(String email);
	
}
