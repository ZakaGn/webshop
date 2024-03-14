package com.example.webshop.exception.apiException.badRequestException;

import com.example.webshop.exception.apiException.APIException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends APIException{
	public BadRequestException(String message){
		super(HttpStatus.NOT_ACCEPTABLE, message);
	}

}