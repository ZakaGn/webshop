package com.example.webshop.controller;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.dto.ProductDTO;
import com.example.webshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest{

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	void getAllCategoriesTest() throws Exception{
		CategoryDto categoryDto = new CategoryDto(1L, "Electronics", "Electronic Items");
		given(productService.getAllCategories()).willReturn(Collections.singletonList(categoryDto));

		mockMvc.perform(get("/products/category").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void createCategory_Success() throws Exception{
		CategoryDto categoryDto = new CategoryDto(null, "Electronics", "Electronic Items");
		CategoryDto savedCategoryDto = new CategoryDto(1L, "Electronics", "Electronic Items");

		given(productService.createCategory(any(CategoryDto.class))).willReturn(savedCategoryDto);

		mockMvc.perform(post("/products/category").contentType(MediaType.APPLICATION_JSON)
		                                          .content(new ObjectMapper().writeValueAsString(categoryDto))).andExpect(
			status().isCreated()).andExpect(content().json(new ObjectMapper().writeValueAsString(savedCategoryDto)));
	}

	@Test
	void deleteCategory_Success() throws Exception{
		doNothing().when(productService).deleteCategory(1L);

		mockMvc.perform(delete("/products/category/1").contentType(MediaType.APPLICATION_JSON)).andExpect(
			status().isNoContent());

		verify(productService, times(1)).deleteCategory(1L);
	}

	@Test
	void getAllProductsTest() throws Exception{
		ProductDTO productDto = new ProductDTO();
		// Mock service method
		given(productService.getAllProducts()).willReturn(Collections.singletonList(productDto));

		mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
			jsonPath("$", hasSize(1)));
	}

	@Test
	void createProductTest() throws Exception{
		ProductDTO productDto = new ProductDTO();
		ProductDTO savedProductDto = new ProductDTO();

		given(productService.createProduct(any(ProductDTO.class))).willReturn(savedProductDto);

		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
		                                 .content(new ObjectMapper().writeValueAsString(productDto))).andExpect(
			status().isCreated());
	}

	@Test
	void getProductByIdTest() throws Exception{
		Long productId = 1L;
		ProductDTO productDto = new ProductDTO();

		given(productService.getProductById(productId)).willReturn(productDto);

		mockMvc.perform(get("/products/{productId}", productId).contentType(MediaType.APPLICATION_JSON)).andExpect(
			status().isOk());
	}

	@Test
	void updateProductTest() throws Exception{
		ProductDTO productDto = new ProductDTO();
		ProductDTO updatedProductDto = new ProductDTO();

		given(productService.updateProduct(any(ProductDTO.class))).willReturn(updatedProductDto);

		mockMvc.perform(put("/products").contentType(MediaType.APPLICATION_JSON)
		                                .content(new ObjectMapper().writeValueAsString(productDto))).andExpect(
			status().isOk());
	}

	@Test
	void deleteProductTest() throws Exception{
		Long productId = 1L;
		doNothing().when(productService).deleteProduct(productId);

		mockMvc.perform(delete("/products/{productId}", productId).contentType(MediaType.APPLICATION_JSON)).andExpect(
			status().isNoContent());

		verify(productService, times(1)).deleteProduct(productId);
	}

}
