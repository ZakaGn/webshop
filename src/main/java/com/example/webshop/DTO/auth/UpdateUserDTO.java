package com.example.webshop.DTO.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
	private String lastName;

	@Email(message = "Email must be a valid email address")
	private String email;
}
