package com.sermaluc.service.controller;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.bean.ErrorResponse;
import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.model.MFile;
import com.sermaluc.service.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/SolicitarProcesamientoArchivo")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo) {
		return fileService.fileProcessingRequest(archivo);
	}

	@GetMapping("/EstadoArchivo/{id}")
	public ResponseEntity<?> getFileStatus(@PathVariable Integer id) throws AccountNotFoundException {
		EstadoArchivoDto estadoArchivo = fileService.findFileStatusById(id);
		if (estadoArchivo == null) {
			throw new AccountNotFoundException("El archivo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
		}
		return new ResponseEntity<>(estadoArchivo, HttpStatus.OK);
	}

	@GetMapping("/DetalleValArchivo/{id}/{codError}")
	public ResponseEntity<?> getDetailValFile(@PathVariable Integer id, @PathVariable Integer codError) throws AccountNotFoundException {
		MFile file = fileService.findAllFileRecordByFileId(id, codError);
		if (file == null) {
			throw new AccountNotFoundException("El archivo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
		}
		return new ResponseEntity<>(file, HttpStatus.OK);
	}

}
