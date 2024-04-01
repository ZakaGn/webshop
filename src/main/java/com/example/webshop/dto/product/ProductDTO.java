package com.example.webshop.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO{
	private Long id;

	@NotBlank(message = "Product name is required")
	@Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
	private String name;

	@NotBlank(message = "Description is required")
	@Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
	private String description;

	@DecimalMin(value = "0.00", inclusive = true, message = "Price must be greater than or equal to 0")
	@Digits(integer = 6, fraction = 2, message = "Price must have up to 6 digits and 2 decimals")
	private float price;

	@Min(value = 0, message = "Quantity must be greater than or equal to 0")
	private int quantity;

	private Long categoryId;

	@Override
	public String toString(){
		return "ProductDTO{" +
			"id=" + id +
			", name='" + name + '\'' +
			", description='" + description + '\'' +
			", price=" + price +
			", quantity=" + quantity +
			", categoryId=" + categoryId +
			'}';
	}

}
