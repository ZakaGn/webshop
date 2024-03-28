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
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private Integer categoryId;

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
