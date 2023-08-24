package com.sermaluc.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.model.MFile;
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
	
	@GetMapping("/EstadoArchivo/{id}")
	public EstadoArchivoDto getFileStatus(@PathVariable Integer id) {
		return fileService.findFileStatusById(id);
	}

	@GetMapping("/DetalleValArchivo/{id}/{codError}")
	public MFile getDetailValFile(@PathVariable Integer id, @PathVariable Integer codError) {
		return fileService.findAllFileRecordByFileId(id, codError);
	}

}
