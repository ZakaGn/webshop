package com.example.webshop.DTO.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginDTOTest{

	private Validator validator;

	@BeforeEach
	public void setUp(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void validLoginDTO(){
		LoginDTO loginDTO = new LoginDTO("user@example.com", "password123");
		Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	void invalidEmailLoginDTO(){
		LoginDTO loginDTO = new LoginDTO("userexample.com", "password123");
		Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
		assertEquals(1, violations.size());
		assertEquals("Email must be a valid email address", violations.iterator().next().getMessage());
	}

	@Test
	void passwordTooShortLoginDTO(){
		LoginDTO loginDTO = new LoginDTO("user@example.com", "pass");
		Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
		assertEquals(1, violations.size());
		assertEquals("Password must be between 6 and 50 characters", violations.iterator().next().getMessage());
	}

	@Test
	void passwordTooLongLoginDTO(){
		String longPassword = "p".repeat(51);
		LoginDTO loginDTO = new LoginDTO("user@example.com", longPassword);
		Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
		assertEquals(1, violations.size());
		assertEquals("Password must be between 6 and 50 characters", violations.iterator().next().getMessage());
	}

}
