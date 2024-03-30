package com.example.webshop.controller;

import com.example.webshop.dto.CartDTO;
import com.example.webshop.dto.CartItemDTO;
import com.example.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController{
	private final OrderService orderService;

	@Autowired
	public CartController(OrderService orderService){
		this.orderService = orderService;
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYER')")
	public ResponseEntity<CartDTO> findByUserId(@PathVariable Long userId){
		CartDTO cartDTO = orderService.findByUserId(userId);
		return ResponseEntity.ok(cartDTO);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYER')")
	public ResponseEntity<CartDTO> addCartItem(@RequestParam Long userId, @RequestBody CartItemDTO cartItemDTO){
		CartDTO cartDTO = orderService.addCartItem(userId, cartItemDTO);
		return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
	}

	@PutMapping("/update/{cartItemId}/{quantity}")
	@PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYER')")
	public ResponseEntity<CartDTO> updateCartItemQuantity(@PathVariable Long cartItemId, @PathVariable int quantity){
		CartDTO cartDTO = orderService.updateCartItemQuantity(cartItemId, quantity);
		return ResponseEntity.ok(cartDTO);
	}

	@DeleteMapping("/remove/{cartItemId}")
	@PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYER')")
	public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId){
		orderService.removeCartItem(cartItemId);
		return ResponseEntity.noContent().build();
	}

}
