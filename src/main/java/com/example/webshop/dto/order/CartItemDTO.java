package com.example.webshop.dto.order;

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
	private String productName;
	private String productPrice;
	private int quantity;

	@Override
	public String toString(){
		return "CartItemDTO{" +
			"id=" + id +
			", cartId=" + cartId +
			", product=" + productName +
			", quantity=" + quantity +
			"}";
	}
}
