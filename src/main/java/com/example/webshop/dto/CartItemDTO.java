package com.example.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO{
	private Long id;
	private Long cartId;
	private Long productId;
	private int quantity;

	@Override
	public String toString(){
		return "CartItemDTO{" +
			"id=" + id +
			", cartId=" + cartId +
			", productId=" + productId +
			", quantity=" + quantity +
			"}";
	}
}
