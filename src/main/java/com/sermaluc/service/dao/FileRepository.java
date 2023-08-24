package com.sermaluc.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sermaluc.service.model.File;

public interface FileRepository extends JpaRepository<File, Integer> {
	
	Optional<File> findOneByName(String name);

}
