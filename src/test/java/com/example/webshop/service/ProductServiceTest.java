package com.example.webshop.service;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.model.Category;
import com.example.webshop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
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
class ProductServiceTest{

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ProductService productService;

	private Category category;
	private CategoryDto categoryDto;

	@BeforeEach
	void setUp() {
		category = new Category(1, "Electronics", "Electronic Items");
		categoryDto = new CategoryDto(1, "Electronics", "Electronic Items");
	}

	/*@Test
	void createCategoryTest() {
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);
		when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

		CategoryDto result = productService.createCategory(categoryDto);

		assertEquals(categoryDto.getName(), result.getName());
		assertEquals(categoryDto.getDescription(), result.getDescription());
	}*/
/*
	@Test
	void updateCategory_Successful() {
		Integer categoryId = 1;
		Category existingCategory = new Category(categoryId, "Electronics", "Electronic Items", null);
		CategoryDto categoryDto = new CategoryDto(categoryId, "Updated Electronics", "Updated Description");

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
		when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArgument(0));

		CategoryDto updatedCategoryDto = productService.updateCategory(categoryId, categoryDto);

		assertEquals(categoryDto.getName(), updatedCategoryDto.getName());
		assertEquals(categoryDto.getDescription(), updatedCategoryDto.getDescription());
		verify(categoryRepository).save(any(Category.class));
	}
*/
	/*@Test
	void getCategoryById_Successful() {
		Integer categoryId = 1;
		Category category = new Category(categoryId, "Electronics", "Electronic Items", null);

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

		CategoryDto result = productService.getCategoryById(categoryId);

		assertEquals(category.getName(), result.getName());
		assertEquals(category.getDescription(), result.getDescription());
	}*/

	/*@Test
	void getAllCategories_Successful() {
		List<Category> categories = List.of(
			new Category(1, "Electronics", "Electronic Items", null),
			new Category(2, "Books", "All kinds of books", null)
		);

		when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDto> result = productService.getAllCategories();

		assertEquals(2, result.size());
		assertEquals("Electronics", result.get(0).getName());
		assertEquals("Books", result.get(1).getName());
	}*/

	@Test
	void deleteCategory_Successful() {
		Integer categoryId = 1;

		doNothing().when(categoryRepository).deleteById(categoryId);

		productService.deleteCategory(categoryId);

		verify(categoryRepository).deleteById(categoryId);
	}

}
