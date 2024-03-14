package com.example.webshop.security;

import com.example.webshop.exception.apiException.badRequestException.AuthenticationException;
import com.example.webshop.exception.apiException.badRequestException.UserNotFoundException;
import com.example.webshop.model.User;
import com.example.webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		User user = loadUserByEmail(authentication.getPrincipal().toString());
		validateAuthentication(authentication, user);
		return new UsernamePasswordAuthenticationToken(
			user.getEmail(),
			user.getPassword(),
			user.getAuthorities()
		);
	}

	@Override
	public boolean supports(Class<?> authentication){
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private User loadUserByEmail(String email) throws UsernameNotFoundException{
		return userRepository.findUserByCredentialsEmail(email)
			.orElseThrow(UserNotFoundException::new);
	}

	private void validateAuthentication(Authentication authentication, User user){
		if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
			throw new AuthenticationException();
	}
}
