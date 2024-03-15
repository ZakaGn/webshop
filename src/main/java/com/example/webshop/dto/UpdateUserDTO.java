package com.example.webshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO{
	@NotBlank(message = "First name is required")
	@Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Size(max = 100, message = "Email must be less than 100 characters")
	private String email;
}
