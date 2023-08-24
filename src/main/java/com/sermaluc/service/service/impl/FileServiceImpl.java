package com.sermaluc.service.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sermaluc.service.bean.ErrorResponse;
import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.dao.FileRecordRepository;
import com.sermaluc.service.dao.FileRepository;
import com.sermaluc.service.enums.EstadoFile;
import com.sermaluc.service.model.MFile;
import com.sermaluc.service.model.MFileRecord;
import com.sermaluc.service.service.FileService;
import com.sermaluc.service.service.RegistroAsyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileDao;
	private final FileRecordRepository fileRecordDao;
	private final RegistroAsyncService registroAsyncService;

	@Override
	public List<MFile> getAll() {
		return fileDao.findAll();
		
	}
	
	@Override
	public EstadoArchivoDto findFileStatusById(Integer id) {
		EstadoArchivoDto result = null;
		Optional<EstadoArchivoDto> lEstadoArchivo = fileDao.findFileStatusById(id.longValue());
		if(!lEstadoArchivo.isEmpty()) {
			result = lEstadoArchivo.get();
			result.setListNOK(fileRecordDao.findFileStatusNokById(id));
		}
		return result;
	}
	
	@Override
	public MFile findAllFileRecordByFileId(Integer id, Integer codError) {
		Optional<MFile> result = fileDao.findDetValFile(id, codError);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	
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

			MFile fileBean = MFile.builder().name(nombreArchivo).status(EstadoFile.PROCESO_VALIDACION).build();
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
			return "El nombre del archivo debe tener 3 valores separados por _";
		}
		
		String fecha = filePart[0];
		String entidad = filePart[1];
		String version = filePart[2].split("\\.")[0];
		String extention = filePart[2].split("\\.")[1];
		
		Integer anio = Integer.parseInt(fecha.substring(0, 4));
		Integer mes = Integer.parseInt(fecha.substring(4, 6));
		Integer dia = Integer.parseInt(fecha.substring(6));
		
		Optional<MFile> bFile = fileDao.findOneByName(nombre);
		
		if(bFile.isPresent()) {
			return "El Archivo se ingreso anteriormente";
		}
		
		if(!extention.equalsIgnoreCase("DAT")) {
			return "La extencion del archivo debe ser DAT";
		}
		
		try{
            LocalDate.of(anio, mes, dia);
        }catch(DateTimeException e) {
        	return "Fecha no válida";
        }
		
		if(!fecha.matches("^\\d*$")) {
			return "Fecha debe ser numérico.";
		}
		
		if(!entidad.matches("^[a-zA-Z]+$")) {
			return "La entidad solo debe tener letras";
		}
		
		if(entidad.length()<5 || entidad.length()>15) {
			return "La cantidad de caracteres de la entidad debe ser mayor a 4 y menor 16";
		}
		
		if(!version.matches("^\\d*$")) {
			return "La versión debe tener solo numeros";
		}
		
		if(version.length()!=3) {
			return "La version debe tener 3 caracteres";
		}
		
		return null;

	}

}
