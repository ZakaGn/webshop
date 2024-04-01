package com.example.webshop.exception.apiException.unauthorizedException;

public class UserNotAuthenticatedException extends UnauthorizedException{
	public UserNotAuthenticatedException(){
		super("User not authenticated");
	}
}
