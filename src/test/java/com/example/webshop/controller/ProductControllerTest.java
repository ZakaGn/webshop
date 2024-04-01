package com.example.webshop.controller;

import com.example.webshop.dto.product.CategoryDto;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest{

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private CategoryDto categoryDto;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp(){
		categoryDto = new CategoryDto();
		productDTO = new ProductDTO();
	}

	@Test
	void getAllCategoriesTest(){
		// Arrange
		when(productService.getAllCategories()).thenReturn(Collections.singletonList(categoryDto));

		// Act
		ResponseEntity<List<CategoryDto>> response = productController.getAllCategories();

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), hasItem(categoryDto));
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void createCategoryTest(){
		// Arrange
		when(productService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

		// Act
		ResponseEntity<CategoryDto> response = productController.createCategory(categoryDto);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody(), is(categoryDto));
	}

	@Test
	void getCategoryByIdTest(){
		// Arrange
		when(productService.getCategoryById(anyLong())).thenReturn(categoryDto);

		// Act
		ResponseEntity<CategoryDto> response = productController.getCategoryById(1L);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is(categoryDto));
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void deleteCategoryTest(){
		// Arrange
		doNothing().when(productService).deleteCategory(anyLong());

		// Act
		ResponseEntity<Void> response = productController.deleteCategory(1L);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
		verify(productService, times(1)).deleteCategory(anyLong());
	}

	@Test
	void getAllProductsTest(){
		// Arrange
		when(productService.getAllProducts()).thenReturn(Collections.singletonList(productDTO));

		// Act
		ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), hasItem(productDTO));
		verify(productService, times(1)).getAllProducts();
	}

	@Test
	void getProductByIdTest(){
		// Arrange
		when(productService.getProductById(anyLong())).thenReturn(productDTO);

		// Act
		ResponseEntity<ProductDTO> response = productController.getProductById(1L);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is(productDTO));
		verify(productService, times(1)).getProductById(anyLong());
	}

	@Test
	void searchProductsByNameTest(){
		// Arrange
		String productName = "test";
		when(productService.searchProductsByName(productName)).thenReturn(Collections.singletonList(productDTO));

		// Act
		ResponseEntity<List<ProductDTO>> response = productController.searchProductsByName(productName);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), hasItem(productDTO));
		verify(productService, times(1)).searchProductsByName(productName);
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void createProductTest(){
		// Arrange
		when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

		// Act
		ResponseEntity<ProductDTO> response = productController.createProduct(productDTO);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody(), is(productDTO));
		verify(productService, times(1)).createProduct(any(ProductDTO.class));
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void updateProductTest(){
		// Arrange
		when(productService.updateProduct(any(ProductDTO.class))).thenReturn(productDTO);

		// Act
		ResponseEntity<ProductDTO> response = productController.updateProduct(productDTO);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is(productDTO));
		verify(productService, times(1)).updateProduct(any(ProductDTO.class));
	}

	@Test
	@WithMockUser(authorities = "EMPLOYER")
	void deleteProductTest(){
		// Arrange
		Long productId = 1L;
		doNothing().when(productService).deleteProduct(productId);

		// Act
		ResponseEntity<Void> response = productController.deleteProduct(productId);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
		verify(productService, times(1)).deleteProduct(productId);
	}

}
