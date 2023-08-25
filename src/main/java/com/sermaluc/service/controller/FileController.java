package com.sermaluc.service.controller;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping("/SolicitarProcesamientoArchivo")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo) {
		try {
			return fileService.fileProcessingRequest(archivo);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/EstadoArchivo/{id}")
	public ResponseEntity<?> getFileStatus(@PathVariable Integer id) {
		try {
			EstadoArchivoDto estadoArchivo = fileService.findFileStatusById(id);
			if (estadoArchivo == null) {
				return new ResponseEntity<>(ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value() + "")
						.message("El archivo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"))
						.build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(estadoArchivo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/DetalleValArchivo/{id}/{codError}")
	public ResponseEntity<?> getDetailValFile(@PathVariable Integer id, @PathVariable Integer codError) {
		try {
			MFile file = fileService.findAllFileRecordByFileId(id, codError);
			if (file == null) {
				return new ResponseEntity<>(ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value() + "")
						.message("El archivo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"))
						.build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(file, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
