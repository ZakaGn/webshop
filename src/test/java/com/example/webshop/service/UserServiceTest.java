package com.example.webshop.service;

import com.example.webshop.dto.LoginDTO;
import com.example.webshop.dto.UserDTO;
import com.example.webshop.model.User;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest{

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtTokenProvider jwtTokenProvider;
	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp(){
	}

	@Test
	void registerUserSuccessfully(){
		// Setup
		UserDTO userDTO = new UserDTO("John", "Doe", "CLIENT");
		userDTO.setEmail("john@example.com");

		when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

		// Action
		UserDTO result = userService.register(userDTO);

		// Assert
		assertNotNull(result);
		assertEquals(userDTO.getEmail(), result.getEmail());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void loginUserSuccessfully() {
		// Setup
		LoginDTO loginDTO = new LoginDTO("john@example.com", "password");
		String expectedToken = "jwtToken";

		when(authenticationManager.authenticate(any(Authentication.class)))
			.thenReturn(mock(Authentication.class));
		when(jwtTokenProvider.generateToken(any(Authentication.class)))
			.thenReturn(expectedToken);

		// Action
		String actualToken = userService.login(loginDTO);

		// Assert
		assertNotNull(actualToken, "Token should not be null");
		assertEquals(expectedToken, actualToken, "Expected token does not match the actual token");

		// Verifications
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtTokenProvider).generateToken(any(Authentication.class));
	}

}
