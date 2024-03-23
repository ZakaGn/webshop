package com.example.webshop.controller;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController{
	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@GetMapping("/category")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categories = productService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	@PostMapping("/category")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory = productService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
		return ResponseEntity.ok(productService.getCategoryById(id));
	}

	@PutMapping("/category/{id}")
	@PreAuthorize("hasRole('EMPLOYER')")
	public ResponseEntity<CategoryDto> updateCategory(
		@PathVariable Integer id, @Valid @RequestBody CategoryDto categoryDto
	){
		CategoryDto updatedCategory = productService.updateCategory(id, categoryDto);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/category/{id}")
	@PreAuthorize("hasRole('EMPLOYER')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
		productService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

}
