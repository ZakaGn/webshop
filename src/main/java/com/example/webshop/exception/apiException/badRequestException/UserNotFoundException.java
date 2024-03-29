package com.example.webshop.exception.apiException.badRequestException;

public class UserNotFoundException extends BadRequestException{
	public UserNotFoundException(){
		super("User not found");
	}

}
