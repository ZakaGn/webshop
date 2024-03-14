package com.example.webshop.exception.apiException.badRequestException;

public class InvalidJwtTokenException extends BadRequestException {
  public InvalidJwtTokenException(String message) {
    super(message);
  }
}
