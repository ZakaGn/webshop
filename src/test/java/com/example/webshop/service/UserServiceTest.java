package com.example.webshop.service;

import com.example.webshop.DTO.auth.*;
import com.example.webshop.exception.apiException.badRequestException.UserNotFoundException;
import com.example.webshop.model.User;
import com.example.webshop.model.auth.Credentials;
import com.example.webshop.model.auth.Role;
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
		RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john@example.com", "pass");

		when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

		// Action
		UserDTO result = userService.register(registerDTO);

		// Assert
		assertNotNull(result);
		assertEquals(registerDTO.getEmail(), result.getEmail());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void loginUserSuccessfully(){
		LoginDTO loginDTO = new LoginDTO("john@example.com", "password");
		String expectedToken = "jwtToken";
		User user = new User();
		Credentials credentials = new Credentials();
		credentials.setEmail(loginDTO.getEmail());
		credentials.setPassword("encodedPassword");
		credentials.setRole(Role.CLIENT);
		user.setCredentials(credentials);

		Authentication auth = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

		when(userRepository.findUserByCredentialsEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
		when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
		when(jwtTokenProvider.generateToken(auth)).thenReturn(expectedToken);

		// When
		LoginResponseDTO actualLogin = userService.login(loginDTO);

		// Then
		assertNotNull(actualLogin.getToken(), "Token should not be null");
		assertEquals(expectedToken, actualLogin.getToken(), "Expected token does not match the actual token");

		// Verifications
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtTokenProvider).generateToken(any(Authentication.class));
	}


	@Test
	void getUserDataByEmail_UserExists_ReturnsUserDTO(){
		// Setup
		String email = "john@example.com";
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		Credentials credentials = new Credentials();
		credentials.setEmail(email);
		credentials.setPassword("encodedPassword");
		credentials.setRole(Role.CLIENT);
		user.setCredentials(credentials);
		when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(user));

		// Action
		UserDTO result = userService.getUserDataByEmail(email);

		// Assert
		assertNotNull(result);
		assertEquals(email, result.getEmail());
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());

		// Verification
		verify(userRepository).findUserByCredentialsEmail(email);
	}

	@Test
	void getUserDataByEmail_UserDoesNotExist_ThrowsException(){
		// Setup
		String email = "john@example.com";
		when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.empty());

		// Action & Assert
		assertThrows(UserNotFoundException.class, () -> userService.getUserDataByEmail(email));

		// Verification
		verify(userRepository).findUserByCredentialsEmail(email);
	}

	@Test
	void updateUserDetails_UserExists_UpdatesDetails(){
		// Setup
		String email = "john@example.com";
		UpdateUserDTO updateUserDTO = new UpdateUserDTO("John", "Updated", email);
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		Credentials credentials = new Credentials();
		credentials.setEmail(email);
		credentials.setPassword("encodedPassword");
		credentials.setRole(Role.CLIENT);
		user.setCredentials(credentials);
		when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

		// Action
		userService.updateUserDetails(updateUserDTO, email);

		// Assert
		assertEquals("Updated", user.getLastName());
		verify(userRepository).save(user);
	}

	@Test
	void updateUserDetails_UserDoesNotExist_ThrowsException(){
		// Setup
		String email = "nonexistent@example.com";
		UpdateUserDTO updateUserDTO = new UpdateUserDTO("John", "Doe", email);
		when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.empty());

		// Action & Assert
		assertThrows(UserNotFoundException.class, () -> userService.updateUserDetails(updateUserDTO, email));

		// Verification
		verify(userRepository, never()).save(any(User.class));
	}

}
