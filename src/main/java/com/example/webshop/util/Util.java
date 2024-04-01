package com.example.webshop.util;

import com.example.webshop.exception.apiException.badRequestException.AuthenticationException;
import com.example.webshop.exception.apiException.badRequestException.UserNotFoundException;
import com.example.webshop.exception.apiException.unauthorizedException.UserNotAuthenticatedException;
import com.example.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Util{
	private static UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository repo){
		Util.userRepository = repo;
	}

	public static Long getUserId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated()){
			throw new UserNotAuthenticatedException();
		}
		Object principal = authentication.getPrincipal();
		if(principal instanceof String){
			return userRepository
				.findUserByCredentialsEmail((String) principal)
				.orElseThrow(UserNotFoundException::new)
			  .getId();
		}else if(principal instanceof UserDetails){
			return userRepository
				.findUserByCredentialsEmail(((UserDetails) principal).getUsername())
				.orElseThrow(UserNotFoundException::new)
				.getId();
		}else{
			throw new AuthenticationException();
		}
	}

}
