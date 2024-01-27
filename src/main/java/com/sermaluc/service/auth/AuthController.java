package com.sermaluc.service.auth;

import java.util.List;

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

import com.sermaluc.service.bean.ErrorResponse;
import com.sermaluc.service.bean.LoginRequest;
import com.sermaluc.service.bean.UserRequest;
import com.sermaluc.service.model.MUser;
import com.sermaluc.service.service.AuthService;
import com.sermaluc.service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			return ResponseEntity.ok(authService.login(request));
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody UserRequest user) {
		try {
			return ResponseEntity.ok(userService.saveUser(user));
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>("User deleted..", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/getAll")
	public ResponseEntity<?> getAll() {
		try {
			List<MUser> listUser = userService.AllUsers();
			if (listUser.isEmpty()) {
				return new ResponseEntity<>(ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value() + "")
						.message("No hay Usuarios en la Base de Datos!").build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(listUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		try {
			MUser user = userService.getUser(id);
			if (user == null) {
				return new ResponseEntity<>(ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value() + "")
						.message("El Usuario ID: ".concat(id.toString()).concat(" no existe en la base de datos!"))
						.build(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
							.message(e.getMessage().concat(": ").concat(e.getLocalizedMessage())).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
