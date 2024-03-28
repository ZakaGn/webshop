package com.example.webshop.service;

import com.example.webshop.dto.CategoryDto;
import com.example.webshop.exception.apiException.badRequestException.CategoryNotFoundException;
import com.example.webshop.model.Category;
import com.example.webshop.repository.CategoryRepository;
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
		Category category = categoryRepository
			.findById(categoryDto.getId())
			.orElseThrow(() -> new CategoryNotFoundException(categoryDto.getId()));
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		Category updatedCategory = categoryRepository.save(category);
		return new CategoryDto(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getDescription());
	}

	public CategoryDto getCategoryById(Integer categoryId){
		Category category = categoryRepository
			.findById(categoryId)
			.orElseThrow(() -> new CategoryNotFoundException(categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	public List<CategoryDto> getAllCategories(){
		return categoryRepository
			.findAll()
			.stream()
			.map(category -> modelMapper.map(category, CategoryDto.class))
		  .collect(Collectors.toList());
	}

	public void deleteCategory(Integer categoryId){
		categoryRepository.deleteById(categoryId);
	}

}
