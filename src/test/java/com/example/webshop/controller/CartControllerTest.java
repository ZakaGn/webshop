package com.example.webshop.controller;

import com.example.webshop.DTO.order.CartDTO;
import com.example.webshop.DTO.order.CartItemDTO;
import com.example.webshop.service.OrderService;
import com.example.webshop.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartControllerTest{

	@Mock
	private OrderService orderService;

	@InjectMocks
	private CartController cartController;

	@BeforeEach
	public void setup(){
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@WithMockUser(authorities = {"CLIENT"})
	public void getCartTest(){
		// Arrange
		CartDTO mockCartDTO = new CartDTO();
		when(orderService.getCart()).thenReturn(mockCartDTO);

		// Act
		ResponseEntity<CartDTO> response = cartController.getCart();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockCartDTO, response.getBody());
	}

	@Test
	@WithMockUser(authorities = {"EMPLOYER"})
	public void getCartByUserIdTest(){
		// Arrange
		Long userId = 1L;
		CartDTO mockCartDTO = new CartDTO();
		when(orderService.getCartByUserId(anyLong())).thenReturn(mockCartDTO);

		// Act
		ResponseEntity<CartDTO> response = cartController.getCartByUserId(userId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockCartDTO, response.getBody());
	}

	@Test
	@WithMockUser(authorities = {"CLIENT"})
	public void addCartItemTest(){
		try(MockedStatic<Util> mockedUtil = Mockito.mockStatic(Util.class)){
			// Arrange
			mockedUtil.when(Util::getUserId).thenReturn(1L);
			CartItemDTO cartItemDTO = new CartItemDTO();
			CartDTO mockCartDTO = new CartDTO();
			when(orderService.addCartItem(anyLong(), any(CartItemDTO.class))).thenReturn(mockCartDTO);

			// Act
			ResponseEntity<CartDTO> response = cartController.addCartItem(cartItemDTO);

			// Assert
			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals(mockCartDTO, response.getBody());
		}
	}

	@Test
	@WithMockUser(authorities = {"CLIENT"})
	public void updateCartItemQuantityTest(){
		// Arrange
		Long cartItemId = 1L;
		int quantity = 5;
		CartDTO mockCartDTO = new CartDTO();
		when(orderService.updateCartItemQuantity(anyLong(), anyInt())).thenReturn(mockCartDTO);

		// Act
		ResponseEntity<CartDTO> response = cartController.updateCartItemQuantity(cartItemId, quantity);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockCartDTO, response.getBody());
	}

	@Test
	@WithMockUser(authorities = {"CLIENT"})
	public void removeCartItemTest(){
		// Arrange
		Long cartItemId = 1L;

		// Act
		ResponseEntity<Void> response = cartController.removeCartItem(cartItemId);

		// Assert
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
