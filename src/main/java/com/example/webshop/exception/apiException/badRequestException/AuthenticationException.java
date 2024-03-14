package com.example.webshop.exception.apiException.badRequestException;

public class AuthenticationException extends BadRequestException{
    public AuthenticationException(){
        super("Incorrect username or password");
    }
}
