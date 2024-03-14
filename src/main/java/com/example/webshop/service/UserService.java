package com.example.webshop.service;

import com.example.webshop.dto.LoginDTO;
import com.example.webshop.dto.UserDTO;
import com.example.webshop.exception.apiException.badRequestException.UserAlreadyExistsException;
import com.example.webshop.model.User;
import com.example.webshop.model.auth.Credentials;
import com.example.webshop.model.auth.Role;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	@Transactional
	public UserDTO register(UserDTO userDto){
		if(userRepository.findUserByCredentialsEmail(userDto.getEmail()).isPresent()){
			throw new UserAlreadyExistsException(userDto.getEmail());
		}
		User newUser = new User();
		newUser.setFirstName(userDto.getFirstName());
		newUser.setLastName(userDto.getLastName());
		Credentials credentials = new Credentials();
		credentials.setEmail(userDto.getEmail());
		credentials.setPassword(passwordEncoder.encode("defaultPassword"));
		credentials.setRole(Role.valueOf(userDto.getRole()));
		newUser.setCredentials(credentials);
		User savedUser = userRepository.save(newUser);
		return new UserDTO(savedUser);
	}

	public String login(LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		return jwtTokenProvider.generateToken(authentication);
	}

}
