package com.sermaluc.service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.model.MFile;

public interface FileService {
	
	public List<MFile> getAll();
	
	public ResponseEntity<?> fileProcessingRequest(MultipartFile archivo);

	public EstadoArchivoDto findFileStatusById(Integer id);

	MFile findAllFileRecordByFileId(Integer id, Integer codError);

}
