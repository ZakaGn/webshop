package com.example.webshop.service;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.dto.ProductDTO;
import com.example.webshop.exception.apiException.badRequestException.CategoryNotFoundException;
import com.example.webshop.exception.apiException.badRequestException.ProductNotFoundException;
import com.example.webshop.model.Category;
import com.example.webshop.model.Product;
import com.example.webshop.repository.CategoryRepository;
import com.example.webshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService{
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	public CategoryDto createCategory(CategoryDto categoryDto){
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		Category savedCategory = categoryRepository.save(category);
		return new CategoryDto(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
	}

	public CategoryDto updateCategory(CategoryDto categoryDto){
		Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(
			() -> new CategoryNotFoundException(categoryDto.getId()));
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		Category updatedCategory = categoryRepository.save(category);
		return new CategoryDto(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getDescription());
	}

	public CategoryDto getCategoryById(Long categoryId){
		Category category = categoryRepository.findById(categoryId).orElseThrow(
			() -> new CategoryNotFoundException(categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	public List<CategoryDto> getAllCategories(){
		return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(
			Collectors.toList());
	}

	public void deleteCategory(Long categoryId){
		categoryRepository.deleteById(categoryId);
	}

	public ProductDTO createProduct(ProductDTO productDTO){
		Product product = modelMapper.map(productDTO, Product.class);
		product.setCategory(
			categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(ProductNotFoundException::new));
		Product savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	public ProductDTO updateProduct(ProductDTO productDTO){
		Product existingProduct =
			productRepository.findById(productDTO.getId()).orElseThrow(ProductNotFoundException::new);
		existingProduct.setName(productDTO.getName());
		existingProduct.setDescription(productDTO.getDescription());
		existingProduct.setPrice(productDTO.getPrice());
		existingProduct.setQuantity(productDTO.getQuantity());
		existingProduct.setCategory(
			categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(CategoryNotFoundException::new));
		Product updatedProduct = productRepository.save(existingProduct);
		return modelMapper.map(updatedProduct, ProductDTO.class);
	}

	public ProductDTO getProductById(Long productId){
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		return modelMapper.map(product, ProductDTO.class);
	}

	public List<ProductDTO> getAllProducts(){
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(
			Collectors.toList());
	}

	public void deleteProduct(Long productId){
		if(!productRepository.existsById(productId)){
			throw new ProductNotFoundException();
		}
		productRepository.deleteById(productId);
	}
}
