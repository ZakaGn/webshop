package com.example.webshop.controller;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.dto.ProductDTO;
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

	@PutMapping("/category")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto updatedCategory = productService.updateCategory(categoryDto);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/category/{id}")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
		productService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO ProductDTO){
		ProductDTO createdProduct = productService.createProduct(ProductDTO);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId){
		ProductDTO product = productService.getProductById(productId);
		return ResponseEntity.ok(product);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO ProductDTO){
		ProductDTO updatedProduct = productService.updateProduct(ProductDTO);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{productId}")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId){
		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}

}
