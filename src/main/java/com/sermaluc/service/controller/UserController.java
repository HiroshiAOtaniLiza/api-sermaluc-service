package com.sermaluc.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sermaluc.service.model.User;
import com.sermaluc.service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
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
