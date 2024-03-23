package com.example.webshop.exception;

import com.example.webshop.exception.apiException.APIException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> handleAPIException(APIException ex){
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
		return new ResponseEntity<>(errorDetails, ex.getStatus());
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class ErrorDetails{
		private String message;
	}

}
