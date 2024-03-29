package com.example.webshop.exception.apiException.badRequestException;

public class OrderNotFoundException extends BadRequestException{
	public OrderNotFoundException(){
		super("Order not found");
	}

}
