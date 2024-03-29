package com.example.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO{
	private Long id;
	private String name;
	private String description;
	private float price;
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
