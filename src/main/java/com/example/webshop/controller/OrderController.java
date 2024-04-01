package com.example.webshop.controller;

import com.example.webshop.dto.CartDTO;
import com.example.webshop.dto.OrderDTO;
import com.example.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController{

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}

	@GetMapping("/get/all")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<List<OrderDTO>> getAllOrders(){
		List<OrderDTO> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/get/{id}")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
		OrderDTO order = orderService.getOrderById(id);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('CLIENT') or hasAuthority('EMPLOYER')")
	public ResponseEntity<List<OrderDTO>> getUserOrders(){
		List<OrderDTO> orders = orderService.getUserOrders();
		return ResponseEntity.ok(orders);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('CLIENT') or hasAuthority('EMPLOYER')")
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
		OrderDTO newOrder = orderService.createOrder(orderDTO);
		return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('CLIENT') or hasAuthority('EMPLOYER')")
	public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderDTO orderDTO){
		OrderDTO updatedOrder = orderService.updateOrder(orderDTO);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('CLIENT') or hasAuthority('EMPLOYER')")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
		orderService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/submit")
	@PreAuthorize("hasAuthority('CLIENT')")
	public ResponseEntity<OrderDTO> submit(@Valid @RequestBody CartDTO cart){
		OrderDTO order = orderService.submit(cart);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

}
