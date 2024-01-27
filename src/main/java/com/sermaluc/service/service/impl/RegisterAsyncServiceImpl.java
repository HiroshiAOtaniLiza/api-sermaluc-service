package com.sermaluc.service.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sermaluc.service.dao.FileRepository;
import com.sermaluc.service.enums.ErrorSeverity;
import com.sermaluc.service.enums.EstadoFile;
import com.sermaluc.service.enums.EstadoFileRecord;
import com.sermaluc.service.model.MFile;
import com.sermaluc.service.model.MFileRecord;
import com.sermaluc.service.service.RegistroAsyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterAsyncServiceImpl implements RegistroAsyncService {

	private final FileRepository fileDao;
	
	@Async
	public void registrarArchivo(String nameArchivo) {
		try {
			Optional<MFile> mFile = fileDao.findOneByName(nameArchivo);
			if(mFile.isPresent()) {
				File archivo = new File("uploads/" + nameArchivo);
				FileReader fr = new FileReader(archivo);
				try (BufferedReader br = new BufferedReader(fr)) {
					String linea;
					int numberLine = 1;
					List<MFileRecord> lFileRecord = new ArrayList<>();
					MFile beanFile = mFile.get();
					beanFile.setStatus(EstadoFile.PROCESADO_OK);
					Map<String, String> Htipos = new HashMap<>(); 
					while ((linea = br.readLine()) != null) {
						MFileRecord fileRecord = validateFile(linea, nameArchivo, numberLine, Htipos);
						lFileRecord.add(fileRecord);
						numberLine++;
						if(fileRecord.getEstado().equals(EstadoFileRecord.OK)) {
							beanFile.setStatus(EstadoFile.PROCESADO_ERROR);
						} else {
							continue;
						}
						if(linea.substring(0,2).equals("01")) {
							Htipos.put(linea.substring(2,17).trim(), "01");
						}
					}
					beanFile.setLFileRecord(lFileRecord);
					fileDao.save(beanFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MFileRecord validateFile(String line, String nameArchivo, int numberLine, Map<String, String> htipos) {
		
		if(line.length()!=25) {
			return MFileRecord.builder()
						.line(numberLine)
						.estado(EstadoFileRecord.ERROR)
						.codigoError(1)
						.gravedadError(ErrorSeverity.G)
						.mensajeError("Cada linea del archivo debe tener un largo exacto de 25 caracteres").build();
		}
		
		String tipo = line.substring(0,2);
		String entidad = line.substring(2,17).trim();
		String fecha = line.substring(17).trim();
		Integer anio = Integer.parseInt(fecha.substring(0, 4));
		Integer mes = Integer.parseInt(fecha.substring(4, 6));
		Integer dia = Integer.parseInt(fecha.substring(6));
		String entidadArchivo = nameArchivo.split("_")[1];
		
		if(!tipo.matches("^\\d*$")) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(300)
					.gravedadError(ErrorSeverity.G)
					.mensajeError("El campo 'Tipo de Registro' debe ser numérico.").build();
		}
		
		if(!entidad.equalsIgnoreCase(entidadArchivo) && !entidad.equalsIgnoreCase("SERMALUC")) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(2)
					.gravedadError(ErrorSeverity.G)
					.mensajeError("El campo debe ser igual a 'SERMALUC' o 'NombreEntidad'.").build();
		} 

		if(htipos.get(entidad)!=null && !htipos.get(entidad).equals("01")) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(202)
					.gravedadError(ErrorSeverity.G)
					.mensajeError("No existe tipo de registro obligatorio.").build();
		}
		
		if(!fecha.matches("^\\d*$")) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(302)
					.gravedadError(ErrorSeverity.G)
					.mensajeError("El campo 'Fecha' debe ser numérico.").build();
		}
		
		if(Integer.parseInt(fecha.substring(0,4))<1995) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(11)
					.gravedadError(ErrorSeverity.M)
					.mensajeError("El año del campo 'Fecha' debe ser mayor o igual a 1995.").build();
		}
		
		try{
            LocalDate.of(anio, mes, dia);
        }catch(DateTimeException e) {
        	return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(100)
					.gravedadError(ErrorSeverity.G)
					.mensajeError("Fecha no válida.").build();
        }
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		LocalDate fecha1 = LocalDate.parse(fecha, formatter);
		LocalDate fecha2 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if(fecha1.isAfter(fecha2)) {
			return MFileRecord.builder()
					.line(numberLine)
					.estado(EstadoFileRecord.ERROR)
					.codigoError(102)
					.gravedadError(ErrorSeverity.L)
					.mensajeError("Fecha no válida.").build();
		}
		
		return MFileRecord.builder()
				.line(numberLine)
				.estado(EstadoFileRecord.OK).build();
	}

}
