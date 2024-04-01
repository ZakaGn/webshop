package com.example.webshop.dto.order;

import com.example.webshop.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO{
	private Long id;
	private Long userId;
	private LocalDateTime orderDate;
	private OrderStatus status;
	private Set<OrderDetailDTO> orderDetails = new HashSet<>();
}
