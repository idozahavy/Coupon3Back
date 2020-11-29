package com.idoz.coupons3.rest;

import java.util.Set;

import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idoz.coupons3.login.LoginManager;
import com.idoz.coupons3.rest.beans.*;
import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.service.ClientService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("login")
@AllArgsConstructor
public class LoginContoller {

	private static final Set<Class<? extends ClientService>> LOGIN_CLIENT_CLASSES = (new Reflections())
			.getSubTypesOf(ClientService.class);

	private LoginManager loginManager;
	private TokenManager tokenManager;

	@PostMapping()
	public ResponseEntity<?> login(@RequestBody LoginCreds creds) {
		ClientService client;
		for (Class<? extends ClientService> clazz : LOGIN_CLIENT_CLASSES) {
			client = loginManager.login(creds.getEmail(), creds.getPassword(), clazz);
			if (client != null) {
				String token = tokenManager.addService(client);
				LoginToken loginToken = new LoginToken(token, clazz.getSimpleName().replace("Service", ""));
				return new ResponseEntity<>(loginToken, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("check")
	public ResponseEntity<?> exist(@RequestHeader(name = "Authorization") String token) {
		System.out.println(String.valueOf(tokenManager.isExist(token))+" , "+token);
		return new ResponseEntity<>(tokenManager.isExist(token), HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String token) {
		tokenManager.removeService(token);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
