package com.example.webshop.exception.apiException.badRequestException;

public class UserAlreadyExistException extends BadRequestException{
	public UserAlreadyExistException(String email){
		super("User with this email already exists (" + email + ")");
	}

}
