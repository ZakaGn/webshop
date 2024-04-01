package com.example.webshop.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto{
	private Long id;

	@NotBlank(message = "Category name is required")
	@Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
	private String name;

	@NotBlank(message = "Description is required")
	@Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
	private String description;
}
