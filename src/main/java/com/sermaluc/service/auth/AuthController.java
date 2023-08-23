package com.sermaluc.service.auth;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sermaluc.service.bean.AuthResponse;
import com.sermaluc.service.bean.LoginRequest;
import com.sermaluc.service.bean.UserRequest;
import com.sermaluc.service.model.User;
import com.sermaluc.service.service.AuthService;
import com.sermaluc.service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthService authService;
	
	@PostMapping(value = "login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("save")
	public ResponseEntity<AuthResponse> saveUser(@RequestBody UserRequest user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	@DeleteMapping("delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return "User deleted..";
	}
	
	@PatchMapping("getAll")
	public List<User> getAll() {
		return userService.AllUsers();
	}
	
	@GetMapping("get/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userService.getUser(id);
	}
	
}
