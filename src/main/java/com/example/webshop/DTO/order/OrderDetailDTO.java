package com.example.webshop.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO{
	private Long id;
	private Long orderId;
	private Long productId;
	private int quantity;
	private float price;
}
