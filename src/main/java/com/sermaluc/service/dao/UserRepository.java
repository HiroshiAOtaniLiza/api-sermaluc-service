package com.sermaluc.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sermaluc.service.model.MUser;

public interface UserRepository extends JpaRepository<MUser, Integer> {

	Optional<MUser> findOneByEmail(String email);
	
}
