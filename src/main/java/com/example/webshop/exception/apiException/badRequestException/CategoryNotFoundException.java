package com.example.webshop.exception.apiException.badRequestException;

public class CategoryNotFoundException extends BadRequestException{
	public CategoryNotFoundException(Long id){
		super("Category with id " + id + " not found");
	}

	public CategoryNotFoundException(){
		super("Category not found");
	}

}
