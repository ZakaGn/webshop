package com.example.webshop.controller;

import com.example.webshop.dto.*;
import com.example.webshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest{
	@Mock
	private UserService userService;
	@Mock
	private Principal principal;
	@InjectMocks
	private UserController userController;
	@BeforeEach
	void setUp(){
		when(principal.getName()).thenReturn("test@example.com");
	}

	@Test
	void registerUser_Successful(){
		RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john@example.com", "password");
		UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com");

		when(userService.register(registerDTO)).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.registerUser(registerDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(userDTO, response.getBody());

		verify(userService).register(registerDTO);
	}

	@Test
	void loginUser_Successful(){
		LoginDTO loginDTO = new LoginDTO("john@example.com", "password");
		String expectedToken = "jwtToken";
		String expectedRole = "CLIENT";
		LoginResponseDTO expectedLogin = new LoginResponseDTO(expectedToken, expectedRole);

		when(userService.login(loginDTO)).thenReturn(expectedLogin);

		ResponseEntity<LoginResponseDTO> response = userController.loginUser(loginDTO);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedToken, Objects.requireNonNull(response.getBody()).getToken(), "Expected token does not match the actual token");

		// Verifications
		verify(userService).login(loginDTO);
	}

	@Test
	void getUserData_Successful(){
		UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com");

		when(userService.getUserDataByEmail(anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.getUserData(principal.getName());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
		verify(userService).getUserDataByEmail(anyString());
	}

	@Test
	void updateUser_Successful(){
		UpdateUserDTO updateUserDTO = new UpdateUserDTO("John", "Doe", "john@example.com");

		doNothing().when(userService).updateUserDetails(updateUserDTO, "test@example.com");

		ResponseEntity<?> response = userController.updateUser(updateUserDTO, principal);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(userService).updateUserDetails(updateUserDTO, "test@example.com");
	}

}
