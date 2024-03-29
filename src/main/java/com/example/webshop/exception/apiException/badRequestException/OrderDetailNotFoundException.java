package com.example.webshop.exception.apiException.badRequestException;

public class OrderDetailNotFoundException extends BadRequestException{
	public OrderDetailNotFoundException(){
		super("Order detail not found");
	}

}
