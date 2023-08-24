package com.sermaluc.service.bean;

import java.util.List;

import lombok.Data;

@Data
public class EstadoArchivoDto {
	
	private String name;
	
	private String status;
	
	private Long countOk;
	
	private List<NokDto> listNOK;

}
