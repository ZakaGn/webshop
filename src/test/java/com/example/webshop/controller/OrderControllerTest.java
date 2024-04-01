package com.example.webshop.controller;

import com.example.webshop.DTO.order.CartDTO;
import com.example.webshop.DTO.order.OrderDTO;
import com.example.webshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest{

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private OrderDTO orderDTO;
	private CartDTO cartDTO;

	@BeforeEach
	void setUp(){
		orderDTO = new OrderDTO();
		cartDTO = new CartDTO();
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void getAllOrdersTest(){
		// Arrange
		List<OrderDTO> orderList = Collections.singletonList(orderDTO);
		when(orderService.getAllOrders()).thenReturn(orderList);

		// Act
		ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orderList, response.getBody());
		verify(orderService, times(1)).getAllOrders();
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void getOrderByIdTest(){
		// Arrange
		Long orderId = 1L;
		when(orderService.getOrderById(orderId)).thenReturn(orderDTO);

		// Act
		ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orderDTO, response.getBody());
		verify(orderService, times(1)).getOrderById(orderId);
	}

	@Test
	@WithMockUser(authorities = {"CLIENT", "EMPLOYER"})
	void getUserOrdersTest(){
		// Arrange
		List<OrderDTO> orderList = Collections.singletonList(orderDTO);
		when(orderService.getUserOrders()).thenReturn(orderList);

		// Act
		ResponseEntity<List<OrderDTO>> response = orderController.getUserOrders();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orderList, response.getBody());
		verify(orderService, times(1)).getUserOrders();
	}

	@Test
	@WithMockUser(authorities = {"CLIENT", "EMPLOYER"})
	void createOrderTest(){
		// Arrange
		when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

		// Act
		ResponseEntity<OrderDTO> response = orderController.createOrder(orderDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(orderDTO, response.getBody());
		verify(orderService, times(1)).createOrder(any(OrderDTO.class));
	}

	@Test
	@WithMockUser(authorities = {"CLIENT", "EMPLOYER"})
	void updateOrderTest(){
		// Arrange
		when(orderService.updateOrder(any(OrderDTO.class))).thenReturn(orderDTO);

		// Act
		ResponseEntity<OrderDTO> response = orderController.updateOrder(orderDTO);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orderDTO, response.getBody());
		verify(orderService, times(1)).updateOrder(any(OrderDTO.class));
	}

	@Test
	@WithMockUser(authorities = {"CLIENT", "EMPLOYER"})
	void deleteOrderTest(){
		// Arrange
		Long orderId = 1L;

		// Act
		ResponseEntity<Void> response = orderController.deleteOrder(orderId);

		// Assert
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(orderService, times(1)).deleteOrder(orderId);
	}

	@Test
	@WithMockUser(authorities = {"CLIENT", "EMPLOYER"})
	void submitTest(){
		// Arrange
		when(orderService.submit(any(CartDTO.class))).thenReturn(orderDTO);

		// Act
		ResponseEntity<OrderDTO> response = orderController.submit(cartDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(orderDTO, response.getBody());
		verify(orderService, times(1)).submit(any(CartDTO.class));
	}

}
