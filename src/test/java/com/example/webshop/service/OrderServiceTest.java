package com.example.webshop.service;

import com.example.webshop.DTO.order.*;
import com.example.webshop.exception.apiException.badRequestException.CartItemNotFoundException;
import com.example.webshop.exception.apiException.badRequestException.OrderNotFoundException;
import com.example.webshop.model.*;
import com.example.webshop.repository.*;
import com.example.webshop.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest{
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private OrderService orderService;

	private User user;
	private Product product;

	@BeforeEach
	void setUp(){
		user = new User();
		user.setId(1L);
		product = new Product();
		product.setId(1L);
		product.setPrice(100);

		lenient().when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		lenient().when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		lenient().when(orderRepository.existsById(anyLong())).thenReturn(true);
		lenient().when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(new CartDTO());
		lenient().when(modelMapper.map(any(OrderDetailDTO.class), eq(OrderDetail.class))).thenAnswer(invocation -> {
			OrderDetailDTO dto = invocation.getArgument(0);
			OrderDetail detail = new OrderDetail();
			detail.setProduct(product);
			detail.setQuantity(dto.getQuantity());
			detail.setPrice(dto.getPrice());
			return detail;
		});

		lenient().when(modelMapper.map(any(OrderDTO.class), eq(Order.class))).thenAnswer(invocation -> {
			OrderDTO dto = invocation.getArgument(0);
			Order order = new Order();
			order.setId(dto.getId());
			return order;
		});
	}

	@Test
	void createOrder_Success(){
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUserId(user.getId());
		Set<OrderDetailDTO> detailDTOs = new HashSet<>();
		OrderDetailDTO detailDTO = new OrderDetailDTO(null, null, product.getId(), 2, 200);
		detailDTOs.add(detailDTO);
		orderDTO.setOrderDetails(detailDTOs);

		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(modelMapper.map(any(Order.class), eq(OrderDTO.class))).thenReturn(new OrderDTO());

		OrderDTO result = orderService.createOrder(orderDTO);

		assertNotNull(result, "Expected non-null OrderDTO result from createOrder");
	}

	@Test
	void getAllOrders_Success(){
		List<Order> orders = List.of(new Order());
		when(orderRepository.findAll()).thenReturn(orders);
		when(modelMapper.map(any(Order.class), eq(OrderDTO.class))).thenReturn(new OrderDTO());

		List<OrderDTO> result = orderService.getAllOrders();

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(orderRepository).findAll();
		verify(modelMapper, times(orders.size())).map(any(Order.class), eq(OrderDTO.class));
	}

	@Test
	void getOrderById_Success(){
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(new OrderDTO());

		OrderDTO result = orderService.getOrderById(orderId);

		assertNotNull(result);
		verify(orderRepository).findById(orderId);
		verify(modelMapper).map(order, OrderDTO.class);
	}

	@Test
	void getOrderById_NotFound(){
		Long orderId = 1L;
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));
	}

	@Test
	void updateOrder_Success(){
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setUserId(1L);
		Order mockOrder = new Order();
		mockOrder.setId(orderDTO.getId());
		when(orderRepository.findById(orderDTO.getId())).thenReturn(Optional.of(mockOrder));
		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(modelMapper.map(any(Order.class), eq(OrderDTO.class))).thenReturn(new OrderDTO());

		// Act:
		OrderDTO result = orderService.updateOrder(orderDTO);

		// Assert:
		assertNotNull(result, "The result of updateOrder should not be null.");
		verify(orderRepository).findById(orderDTO.getId());
		verify(orderRepository).save(any(Order.class));
		verify(modelMapper).map(any(Order.class), eq(OrderDTO.class));
	}

	@Test
	void updateOrder_NotFound(){
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);

		when(orderRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderDTO));
	}

	@Test
	void deleteOrder_Success(){
		Long orderId = 1L;
		doNothing().when(orderRepository).deleteById(orderId);

		orderService.deleteOrder(orderId);

		verify(orderRepository).deleteById(orderId);
	}

	@Test
	void deleteOrder_NotFound(){
		Long orderId = 1L;
		doThrow(new OrderNotFoundException()).when(orderRepository).deleteById(orderId);

		assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(orderId));
	}

	@Test
	void addCartItem_Success(){
		Long userId = 1L;
		Cart cart = new Cart();
		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
		when(cartRepository.save(any(Cart.class))).thenReturn(cart);

		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setProductId(1L);
		cartItemDTO.setQuantity(1);

		CartDTO result = orderService.addCartItem(userId, cartItemDTO);

		assertNotNull(result);
		verify(cartRepository).findByUserId(userId);
		verify(productRepository).findById(cartItemDTO.getProductId());
		verify(cartRepository).save(any(Cart.class));
	}

	@Test
	void updateCartItemQuantity_Success(){
		Long cartItemId = 1L;
		int newQuantity = 3;

		CartItem cartItem = new CartItem();
		cartItem.setId(cartItemId);
		cartItem.setQuantity(1);
		Cart cart = new Cart();
		cartItem.setCart(cart);

		when(cartRepository.findCartItemById(cartItemId)).thenReturn(Optional.of(cartItem));
		when(cartRepository.save(any(Cart.class))).thenReturn(cart);

		CartDTO result = orderService.updateCartItemQuantity(cartItemId, newQuantity);

		assertNotNull(result, "The result should not be null.");
		assertEquals(newQuantity, cartItem.getQuantity(), "The quantity should be updated.");
		verify(cartRepository).save(cart);
	}

	@Test
	void updateCartItemQuantity_NotFound(){
		when(cartRepository.findCartItemById(anyLong())).thenReturn(Optional.empty());

		assertThrows(CartItemNotFoundException.class, () -> orderService.updateCartItemQuantity(1L, 2));
	}

	@Test
	void removeCartItem_Success(){
		Long cartItemId = 1L;

		CartItem cartItem = new CartItem();
		cartItem.setId(cartItemId);
		Cart cart = new Cart();
		cartItem.setCart(cart);
		cart.getCartItems().add(cartItem);

		when(cartRepository.findCartItemById(cartItemId)).thenReturn(Optional.of(cartItem));

		orderService.removeCartItem(cartItemId);

		assertTrue(cart.getCartItems().isEmpty(), "The cart item should be removed.");
		verify(cartRepository).save(cart);
	}

	@Test
	void removeCartItem_NotFound(){
		when(cartRepository.findCartItemById(anyLong())).thenReturn(Optional.empty());

		assertThrows(CartItemNotFoundException.class, () -> orderService.removeCartItem(1L));
	}

	@Test
	void getCart_Success(){
		Long userId = 1L;
		Cart cart = new Cart();
		cart.setUser(user);

		try(MockedStatic<Util> mockedStatic = Mockito.mockStatic(Util.class)){
			mockedStatic.when(Util::getUserId).thenReturn(userId);

			when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
			when(modelMapper.map(cart, CartDTO.class)).thenReturn(new CartDTO());

			CartDTO result = orderService.getCart();

			assertNotNull(result, "The result should not be null.");
			verify(cartRepository).findByUserId(userId);
			verify(modelMapper).map(cart, CartDTO.class);
		}
	}

	@Test
	void getCartByUserId_Success(){
		Long userId = 1L;
		Cart cart = new Cart();
		cart.setUser(user);

		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(new CartDTO());

		CartDTO result = orderService.getCartByUserId(userId);

		assertNotNull(result, "The result should not be null.");
		verify(cartRepository).findByUserId(userId);
		verify(modelMapper).map(cart, CartDTO.class);
	}

}
