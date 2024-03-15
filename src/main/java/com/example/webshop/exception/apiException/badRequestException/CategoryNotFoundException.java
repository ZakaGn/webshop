package com.example.webshop.exception.apiException.badRequestException;

public class CategoryNotFoundException extends BadRequestException{
	public CategoryNotFoundException(Integer id){
		super("Category with id " + id + " not found");
	}

}
