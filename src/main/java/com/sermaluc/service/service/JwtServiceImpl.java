package com.sermaluc.service.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtServiceImpl {
	
	String getusernameFromToken(String token) throws Exception;
	
	String getToken(UserDetails user);
	
	boolean isTokenValid(String token, UserDetails userDetails);
	
}
