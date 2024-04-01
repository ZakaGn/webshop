package com.example.webshop.DTO.auth;

import com.example.webshop.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO{
	@NotBlank(message = "Id is required")
	private Long id;

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

	@NotBlank(message = "Role is required")
	private String role;

	public UserDTO(String firstName, String lastName, String role){
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public UserDTO(User user){
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.role = user.getRole().toString();
	}
}
