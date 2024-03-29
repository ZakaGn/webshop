package com.example.webshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto{
	private Long id;

	@NotBlank(message = "Category name is required")
	@Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
	private String name;

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String description;
}
