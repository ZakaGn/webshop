package com.example.webshop.service;

import com.example.webshop.dto.OrderDTO;
import com.example.webshop.dto.OrderDetailDTO;
import com.example.webshop.exception.apiException.badRequestException.OrderDetailNotFoundException;
import com.example.webshop.exception.apiException.badRequestException.OrderNotFoundException;
import com.example.webshop.exception.apiException.badRequestException.ProductNotFoundException;
import com.example.webshop.exception.apiException.badRequestException.UserNotFoundException;
import com.example.webshop.model.Order;
import com.example.webshop.model.OrderDetail;
import com.example.webshop.model.Product;
import com.example.webshop.model.User;
import com.example.webshop.repository.OrderDetailRepository;
import com.example.webshop.repository.OrderRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService{

	private final OrderRepository orderRepository;

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	private final OrderDetailRepository orderDetailRepository;

	private final ModelMapper modelMapper;

	public OrderService(
		OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository,
		OrderDetailRepository orderDetailRepository, ModelMapper modelMapper
	){
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public OrderDTO createOrder(OrderDTO orderDTO){
		User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(UserNotFoundException::new);
		Order order = modelMapper.map(orderDTO, Order.class);
		order.setUser(user);
		order.setOrderDetails(new HashSet<>());
		for(OrderDetailDTO detailDTO : orderDTO.getOrderDetails()){
			Product product = productRepository.findById(detailDTO.getProductId()).orElseThrow(ProductNotFoundException::new);
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
				if(detail == null){
					throw new OrderDetailNotFoundException();
				}
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
		if(!orderRepository.existsById(id)){
			throw new OrderNotFoundException();
		}
		orderRepository.deleteById(id);
	}

}
