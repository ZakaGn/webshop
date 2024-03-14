package com.example.webshop.exception.apiException.badRequestException;

public class UserAlreadyExistsException extends BadRequestException{
	public UserAlreadyExistsException(String email){
		super("User with email " + email + " already exists");
	}

}
