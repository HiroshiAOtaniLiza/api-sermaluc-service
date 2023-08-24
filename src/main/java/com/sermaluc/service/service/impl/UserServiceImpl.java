package com.sermaluc.service.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sermaluc.service.bean.AuthResponse;
import com.sermaluc.service.bean.UserRequest;
import com.sermaluc.service.dao.UserRepository;
import com.sermaluc.service.model.MFeature;
import com.sermaluc.service.model.MUser;
import com.sermaluc.service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userDao;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public List<MUser> AllUsers() {
		return userDao.findAll();
	}

	@Override
	public MUser getUser(Integer id) {
		Optional<MUser> result = userDao.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public AuthResponse saveUser(UserRequest request) {
		
		MUser user = MUser.builder()
							.id(request.getId())
							.email(request.getEmail())
							.lastname(request.getLastname())
							.name(request.getName())
							.password(passwordEncoder.encode(request.getPassword()))
							.feature(MFeature.builder().id(request.getFeatureId()).build()).build();

		userDao.save(user);

		return AuthResponse.builder().token(jwtService.getToken(user)).build();
	}

	@Override
	public void deleteUser(Integer id) {
		userDao.deleteById(id);
	}

}
