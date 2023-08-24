package com.sermaluc.service.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sermaluc.service.service.RegistroAsyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroAsyncServiceImpl implements RegistroAsyncService {

	@Async
	public void registrarArchivo(String nameArchivo) {
		try {
			File archivo = new File("uploads/" + nameArchivo);
			FileReader fr = new FileReader(archivo);
			try (BufferedReader br = new BufferedReader(fr)) {
				String linea;
				while ((linea = br.readLine()) != null) {
					System.out.println(linea);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
