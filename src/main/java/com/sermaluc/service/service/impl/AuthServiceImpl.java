package com.sermaluc.service.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sermaluc.service.bean.AuthResponse;
import com.sermaluc.service.bean.LoginRequest;
import com.sermaluc.service.dao.UserRepository;
import com.sermaluc.service.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	
	@Override
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails user=userRepository.findOneByEmail(request.getUsername()).orElseThrow();
		String token=jwtService.getToken(user);
		return AuthResponse.builder().token(token).build();
	}

}
