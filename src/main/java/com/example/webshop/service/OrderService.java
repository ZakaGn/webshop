package com.example.webshop.service;

import com.example.webshop.dto.order.CartDTO;
import com.example.webshop.dto.order.CartItemDTO;
import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.dto.order.OrderDetailDTO;
import com.example.webshop.exception.apiException.badRequestException.*;
import com.example.webshop.exception.apiException.unauthorizedException.UserNotAuthenticatedException;
import com.example.webshop.model.*;
import com.example.webshop.repository.OrderRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.webshop.repository.CartRepository;

@Service
public class OrderService{
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public OrderService(
		OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository,
		ProductRepository productRepository, ModelMapper modelMapper
	){
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public OrderDTO createOrder(OrderDTO orderDTO){
		User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(UserNotFoundException::new);
		Order order = modelMapper.map(orderDTO, Order.class);
		order.setUser(user);
		order.setOrderDetails(new HashSet<>());
		for(OrderDetailDTO detailDTO : orderDTO.getOrderDetails()){
			Product product =
				productRepository.findById(detailDTO.getProductId()).orElseThrow(ProductNotFoundException::new);
			OrderDetail detail = modelMapper.map(detailDTO, OrderDetail.class);
			detail.setProduct(product);
			detail.setOrder(order);
			order.getOrderDetails().add(detail);
		}
		Order savedOrder = orderRepository.save(order);
		return modelMapper.map(savedOrder, OrderDTO.class);
	}

	@Transactional(readOnly = true)
	public List<OrderDTO> getAllOrders(){
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public OrderDTO getOrderById(Long id){
		Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
		return modelMapper.map(order, OrderDTO.class);
	}

	@Transactional(readOnly = true)
	public List<OrderDTO> getUserOrders(){
		Long userId = Util.getUserId();
		List<Order> orders = orderRepository.findByUserId(userId).orElseThrow(OrderNotFoundException::new);
		return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO updateOrder(OrderDTO orderDTO){
		Order existingOrder = orderRepository.findById(orderDTO.getId()).orElseThrow(OrderNotFoundException::new);
		existingOrder.setStatus(orderDTO.getStatus());
		Map<Long, OrderDetail> existingDetailsMap = existingOrder.getOrderDetails().stream().collect(
			Collectors.toMap(OrderDetail::getId, detail -> detail));
		Set<OrderDetail> updatedDetails = new HashSet<>();
		for(OrderDetailDTO detailDTO : orderDTO.getOrderDetails()){
			OrderDetail detail;
			if(detailDTO.getId() != null){
				detail = existingDetailsMap.get(detailDTO.getId());
				if(detail == null) throw new OrderDetailNotFoundException();
			}else{
				detail = new OrderDetail();
				detail.setOrder(existingOrder);
			}
			detail.setQuantity(detailDTO.getQuantity());
			detail.setPrice(detailDTO.getPrice());
			updatedDetails.add(detail);
		}
		existingOrder.getOrderDetails().clear();
		existingOrder.getOrderDetails().addAll(updatedDetails);
		Order updatedOrder = orderRepository.save(existingOrder);
		return modelMapper.map(updatedOrder, OrderDTO.class);
	}

	@Transactional
	public void deleteOrder(Long id){
		if(!orderRepository.existsById(id)) throw new OrderNotFoundException();
		orderRepository.deleteById(id);
	}

	@Transactional
	public OrderDTO submit(CartDTO cartDTO){
		Long userId = Util.getUserId();
		User user = userRepository.findById(cartDTO.getUserId()).orElseThrow(UserNotAuthenticatedException::new);
		if(!userId.equals(user.getId())) throw new UserNotAuthenticatedException();
		if(cartDTO.getCartItems().isEmpty()) throw new EmptyCartException();
		Order order = new Order();
		order.setUser(user);
		order.setOrderDetails(new HashSet<>());
		for(CartItemDTO cartItemDTO : cartDTO.getCartItems()){
			Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(
				ProductNotFoundException::new);
			OrderDetail detail = new OrderDetail();
			detail.setProduct(product);
			detail.setQuantity(cartItemDTO.getQuantity());
			detail.setPrice(product.getPrice());
			detail.setOrder(order);
			order.getOrderDetails().add(detail);
		}
		Order savedOrder = orderRepository.save(order);
		Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(CartNotFoundException::new);
		cart.getCartItems().clear();
		cartRepository.save(cart);
		return modelMapper.map(savedOrder, OrderDTO.class);
	}

	@Transactional
	public CartDTO addCartItem(Long userId, CartItemDTO cartItemDTO){
		final boolean[] update = {false};
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(CartNotFoundException::new);
		Product product =
			productRepository.findById(cartItemDTO.getProductId()).orElseThrow(ProductNotFoundException::new);
		cart.getCartItems().forEach(cartItem -> {
			if(cartItem.getProduct().getId().equals(product.getId())){
				cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
				update[0] = true;
			}
		});
		if(update[0]){
			cartRepository.save(cart);
			return modelMapper.map(cart, CartDTO.class);
		}
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(cartItemDTO.getQuantity());
		cart.addCartItem(cartItem);
		cartRepository.save(cart);
		return modelMapper.map(cart, CartDTO.class);
	}

	@Transactional
	public CartDTO updateCartItemQuantity(Long cartItemId, int quantity){
		CartItem cartItem = cartRepository.findCartItemById(cartItemId).orElseThrow(CartItemNotFoundException::new);
		cartItem.setQuantity(quantity);
		Cart cart = cartItem.getCart();
		cartRepository.save(cart);
		return modelMapper.map(cart, CartDTO.class);
	}

	@Transactional
	public void removeCartItem(Long cartItemId){
		CartItem cartItem = cartRepository.findCartItemById(cartItemId).orElseThrow(CartItemNotFoundException::new);
		Cart cart = cartItem.getCart();
		cart.removeCartItem(cartItem);
		cartRepository.save(cart);
	}

	@Transactional(readOnly = true)
	public CartDTO getCart(){
		Cart cart = cartRepository.findByUserId(Util.getUserId()).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(cart, CartDTO.class);
	}

	@Transactional(readOnly = true)
	public CartDTO getCartByUserId(Long userId){
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(cart, CartDTO.class);
	}

}
