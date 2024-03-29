package com.sermaluc.service.service;

import java.util.List;

import com.sermaluc.service.bean.AuthResponse;
import com.sermaluc.service.bean.UserRequest;
import com.sermaluc.service.model.MUser;

public interface UserService {
	
	List<MUser> AllUsers();
	
	MUser getUser(Integer id);
	
	AuthResponse saveUser(UserRequest user);

	void deleteUser(Integer id);

}
