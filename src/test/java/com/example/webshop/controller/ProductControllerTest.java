package com.example.webshop.controller;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	void getAllCategoriesTest() throws Exception {
		CategoryDto categoryDto = new CategoryDto(1, "Electronics", "Electronic Items");
		given(productService.getAllCategories()).willReturn(Collections.singletonList(categoryDto));

		mockMvc
			.perform(get("/products/category")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void createCategory_Success() throws Exception {
		CategoryDto categoryDto = new CategoryDto(null, "Electronics", "Electronic Items");
		CategoryDto savedCategoryDto = new CategoryDto(1, "Electronics", "Electronic Items");

		given(productService.createCategory(any(CategoryDto.class))).willReturn(savedCategoryDto);

		mockMvc
			.perform(post("/products/category")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(categoryDto)))
			.andExpect(status().isCreated())
			.andExpect(content().json(new ObjectMapper().writeValueAsString(savedCategoryDto)));
	}

	/*
	@Test
	void updateCategory_Success() throws Exception {
		CategoryDto categoryDto = new CategoryDto(1, "Electronics", "Updated Description");
		given(productService.updateCategory(eq(1), any(CategoryDto.class))).willReturn(categoryDto);

		mockMvc
			.perform(put("/products/category/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(categoryDto)))
			.andExpect(status().isOk())
			.andExpect(content().json(new ObjectMapper().writeValueAsString(categoryDto)));
	}
	*/

	@Test
	void deleteCategory_Success() throws Exception {
		doNothing().when(productService).deleteCategory(1);

		mockMvc
			.perform(delete("/products/category/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		verify(productService, times(1)).deleteCategory(1);
	}

}
