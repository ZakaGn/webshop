package com.example.webshop.exception.apiException.badRequestException;

public class EmptyCartException extends BadRequestException{
	public EmptyCartException(){
		super("Cart is empty");
	}
}
