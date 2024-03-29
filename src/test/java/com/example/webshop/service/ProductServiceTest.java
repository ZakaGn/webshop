package com.example.webshop.service;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.dto.ProductDTO;
import com.example.webshop.model.Category;
import com.example.webshop.model.Product;
import com.example.webshop.repository.CategoryRepository;
import com.example.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest{

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ProductService productService;

	private Product product;
	private ProductDTO productDTO;
	private Category category;

	@BeforeEach
	void setUp(){
		category = new Category(1L, "Electronics", null);
		product = new Product(1L, "Laptop", "A high-performance laptop.", 1200, 10, category);
		productDTO = new ProductDTO(1L, "Laptop", "A high-performance laptop.", 1200, 10, 1L);
	}

	@Test
	void createProductSuccess(){
		when(categoryRepository.findById(productDTO.getCategoryId())).thenReturn(java.util.Optional.of(category));
		when(modelMapper.map(any(ProductDTO.class), eq(Product.class))).thenReturn(product);
		when(productRepository.save(any(Product.class))).thenReturn(product);
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

		ProductDTO savedProductDTO = productService.createProduct(productDTO);

		assertNotNull(savedProductDTO);
		assertEquals(productDTO.getName(), savedProductDTO.getName());
		verify(productRepository).save(any(Product.class));
	}

	@Test
	void updateProductSuccess(){
		when(productRepository.findById(productDTO.getId())).thenReturn(java.util.Optional.of(product));
		when(categoryRepository.findById(productDTO.getCategoryId())).thenReturn(java.util.Optional.of(category));
		when(productRepository.save(any(Product.class))).thenReturn(product);
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

		ProductDTO updatedProductDTO = productService.updateProduct(productDTO);

		assertNotNull(updatedProductDTO);
		assertEquals(productDTO.getName(), updatedProductDTO.getName());
		verify(productRepository).save(product);
	}

	@Test
	void getProductByIdSuccess(){
		when(productRepository.findById(product.getId())).thenReturn(java.util.Optional.of(product));
		when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

		ProductDTO foundProductDTO = productService.getProductById(product.getId());

		assertNotNull(foundProductDTO);
		assertEquals(product.getName(), foundProductDTO.getName());
	}

	@Test
	void createCategory_Success(){
		CategoryDto categoryDto = new CategoryDto();
		Category category = new Category();

		lenient().when(modelMapper.map(any(CategoryDto.class), eq(Category.class))).thenReturn(category);
		lenient().when(categoryRepository.save(any(Category.class))).thenReturn(category);
		lenient().when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

		CategoryDto createdCategory = productService.createCategory(categoryDto);

		assertNotNull(createdCategory);
		verify(categoryRepository).save(any(Category.class));
	}

	@Test
	void updateCategory_Success(){
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(1L);
		Category existingCategory = new Category();

		lenient().when(categoryRepository.findById(categoryDto.getId())).thenReturn(java.util.Optional.of(existingCategory));
		lenient().when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
		lenient().when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

		CategoryDto updatedCategory = productService.updateCategory(categoryDto);

		assertNotNull(updatedCategory);
		verify(categoryRepository).save(any(Category.class));
	}

}
