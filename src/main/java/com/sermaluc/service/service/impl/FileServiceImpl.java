package com.sermaluc.service.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.bean.ErrorResponse;
import com.sermaluc.service.dao.FileRepository;
import com.sermaluc.service.enums.Estados;
import com.sermaluc.service.model.File;
import com.sermaluc.service.service.FileService;
import com.sermaluc.service.service.RegistroAsyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileDao;
	
	private final RegistroAsyncService registroAsyncService;

	@Override
	public ResponseEntity<?> fileProcessingRequest(MultipartFile archivo) {
		Map<String, Object> response = new HashMap<>();

		if (!archivo.isEmpty()) {
			String nombreArchivo = archivo.getOriginalFilename();
			
			String mensajeError = validarNombreArchivo(nombreArchivo);
			
			if (mensajeError != null) {
				response.put("mensaje", mensajeError);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				return new ResponseEntity<>(
						ErrorResponse.builder().message("Error al subir el archivo: " + nombreArchivo).code("500")
								.error(e.getMessage().concat(": ").concat(e.getCause().getMessage())).build(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			File fileBean = File.builder().name(nombreArchivo).estado(Estados.PROCESO_VALIDACION).build();
			fileBean = fileDao.save(fileBean);
			
			registroAsyncService.registrarArchivo(nombreArchivo);
			
			response.put("file", fileBean);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.put("mensaje", "Archivo Vacio");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private String validarNombreArchivo(String nombre) {
		String[] filePart = nombre.split("_");
		
		if(filePart.length<3) {
			return "El nombre del archivo debe tener 3 valores separador por _";
		}
		
		String fecha = filePart[0];
		String entidad = filePart[1];
		String version = filePart[2].split("\\.")[0];
		String extention = filePart[2].split("\\.")[1];

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		LocalDate fecha1 = LocalDate.parse(fecha, formatter);
		LocalDate fecha2 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Optional<File> bFile = fileDao.findOneByName(nombre);
		
		if(bFile.isPresent()) {
			return "El Archivo se ingreso anteriormente";
		}
		
		if(!extention.equalsIgnoreCase("DAT")) {
			return "La extencion del archivo debe ser DAT";
		}
		
		if(fecha1.isAfter(fecha2)) {
			return "Fecha debe ser menor o igual a la fecha actual";
		}
		
		if(!entidad.matches("^[a-zA-Z]+$")) {
			return "La entidad solo debe tener letras";
		}
		
		if(entidad.length()<5 || entidad.length()>15) {
			return "La cantidad de caracteres de la entidad debe ser mayor a 4 y menor 16";
		}
		
		if(!version.matches("^[0-1]+$")) {
			return "La versión debe tener solo numeros";
		}
		
		if(version.length()!=3) {
			return "La version debe tener 3 caracteres";
		}
		
		return null;

	}

}
