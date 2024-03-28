package com.example.webshop.exception.apiException.badRequestException;

public class ProductNotFoundException extends BadRequestException{
	public ProductNotFoundException(){
		super("Product not found");
	}

}
