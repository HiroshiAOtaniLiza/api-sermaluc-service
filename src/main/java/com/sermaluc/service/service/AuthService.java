package com.sermaluc.service.service;

import com.sermaluc.service.bean.AuthResponse;
import com.sermaluc.service.bean.LoginRequest;

public interface AuthService {

	AuthResponse login(LoginRequest request);

}
