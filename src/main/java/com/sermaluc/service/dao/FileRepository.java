package com.sermaluc.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.model.MFile;

public interface FileRepository extends JpaRepository<MFile, Integer> {
	
	Optional<MFile> findOneByName(String name);
	
	@Query(nativeQuery = true)
	Optional<EstadoArchivoDto> findFileStatusById(Long id);
	
	@Query(value = "SELECT f FROM MFile f INNER JOIN FETCH f.lFileRecord r where f.id=?1 and r.codigoError = ?2")
	Optional<MFile> findDetValFile(Integer id, Integer cod);

}
