package com.example.webshop.controller;

import com.example.webshop.dto.*;
import com.example.webshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController{

	private final UserService userService;

	public UserController(UserService userService){
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO){
		UserDTO registeredUser = userService.register(registerDTO);
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO){
		String token = userService.login(loginDTO);
		return ResponseEntity.ok(new TokenDTO(token));
	}

	@GetMapping("/data")
	public ResponseEntity<UserDTO> getUserData(@AuthenticationPrincipal String email){
		UserDTO userDTO = userService.getUserDataByEmail(email);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO, Principal principal){
		System.out.println("Principal: " + principal.getName());
		userService.updateUserDetails(updateUserDTO, principal.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
