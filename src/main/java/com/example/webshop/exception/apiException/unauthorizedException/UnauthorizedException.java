package com.example.webshop.exception.apiException.unauthorizedException;

import com.example.webshop.exception.apiException.APIException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends APIException{
	public UnauthorizedException(String message){
		super(HttpStatus.UNAUTHORIZED, message);
	}

}
