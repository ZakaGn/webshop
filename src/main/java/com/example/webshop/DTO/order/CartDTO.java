package com.example.webshop.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO{
	private Long id;
	private Long userId;
	private LocalDateTime createdAt;
	private List<CartItemDTO> cartItems;

	@Override
	public String toString(){
		return "CartDTO{" +
			"id=" + id +
			", userId=" + userId +
			", cartItems=" + cartItems +
			", createdAt=" + createdAt +
			"}";
	}
}
