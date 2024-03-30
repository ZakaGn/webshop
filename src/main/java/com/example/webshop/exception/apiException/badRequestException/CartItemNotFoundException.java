package com.example.webshop.exception.apiException.badRequestException;

public class CartItemNotFoundException extends BadRequestException{
	public CartItemNotFoundException(){
		super("Cart item not found");
	}

}
