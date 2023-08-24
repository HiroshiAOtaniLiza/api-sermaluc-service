package com.sermaluc.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;
	
	@PostMapping("/SolicitarProcesamientoArchivo")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo) {
		return fileService.fileProcessingRequest(archivo);
	}

}
