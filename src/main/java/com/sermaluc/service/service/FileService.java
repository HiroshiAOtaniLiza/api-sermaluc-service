package com.sermaluc.service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	ResponseEntity<?> fileProcessingRequest(MultipartFile archivo);
	
}
