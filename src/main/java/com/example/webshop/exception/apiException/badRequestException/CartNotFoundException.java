package com.example.webshop.exception.apiException.badRequestException;

public class CartNotFoundException extends BadRequestException{
	public CartNotFoundException(){
		super("Cart not found");
	}
}
