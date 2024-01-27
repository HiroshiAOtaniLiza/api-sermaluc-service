package com.sermaluc.service.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sermaluc.service.bean.ErrorResponse;

@ControllerAdvice
public class ExceptionConfig {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e) {
		return new ResponseEntity<>(
				ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
						.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
