package com.example.webshop.controller;

import com.example.webshop.dto.product.CategoryDto;
import com.example.webshop.dto.product.ProductDTO;
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
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
		CategoryDto createdCategory = productService.createCategory(category);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getCategoryById(id));
	}

	@PutMapping("/category")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category){
		CategoryDto updatedCategory = productService.updateCategory(category);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/category/{id}")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		productService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
		ProductDTO product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductDTO>> searchProductsByName(@PathVariable String name){
		List<ProductDTO> products = productService.searchProductsByName(name);
		return ResponseEntity.ok(products);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product){
		ProductDTO createdProduct = productService.createProduct(product);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO product){
		ProductDTO updatedProduct = productService.updateProduct(product);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('EMPLOYER')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

}
