package com.sermaluc.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sermaluc.service.bean.NokDto;
import com.sermaluc.service.model.MFileRecord;

public interface FileRecordRepository extends JpaRepository<MFileRecord, Integer> {

	@Query(nativeQuery = true)
	List<NokDto> findFileStatusNokById(Integer id);
	
}
