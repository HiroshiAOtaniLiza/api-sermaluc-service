package com.sermaluc.service.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sermaluc.service.bean.LoginRequest;
import com.sermaluc.service.bean.UserRequest;
import com.sermaluc.service.model.MUser;
import com.sermaluc.service.service.AuthService;
import com.sermaluc.service.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody UserRequest user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted..", HttpStatus.OK);
	}

	@PatchMapping("/getAll")
	public ResponseEntity<?> getAll() throws InterruptedException {
		List<MUser> listUser = userService.AllUsers();
		if (listUser.isEmpty()) {
			throw new InterruptedException("No hay Usuarios en la Base de Datos!");
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) throws InterruptedException {
		MUser user = userService.getUser(id);
		if (user == null) {
			throw new InterruptedException("El Usuario ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
		}
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

}
