package com.example.webshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

	@Test
	void testEquals() {
		Category category1 = new Category(1L, "Electronics", "Electronic items including but not limited to smartphones, TVs, and laptops.");
		Category category2 = new Category(1L, "Electronics", "Electronic items including but not limited to smartphones, TVs, and laptops.");
		Category category3 = new Category(2L, "Books", "All kinds of books");

		assertEquals(category1, category2, "Two categories with the same id should be equal");
		assertNotEquals(category1, category3, "Two categories with different ids should not be equal");
	}

	@Test
	void testHashCode() {
		Category category1 = new Category(1L, "Electronics", "Electronic items including but not limited to smartphones, TVs, and laptops.");
		Category category2 = new Category(1L, "Electronics", "Electronic items including but not limited to smartphones, TVs, and laptops.");

		assertEquals(category1.hashCode(), category2.hashCode(), "Hashcode should be the same for two equal objects");
	}

	@Test
	void testSetterAndGetters() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Electronics");
		category.setDescription("Electronic items including but not limited to smartphones, TVs, and laptops.");

		assertEquals(1, category.getId(), "Getter for id failed");
		assertEquals("Electronics", category.getName(), "Getter for name failed");
		assertEquals("Electronic items including but not limited to smartphones, TVs, and laptops.", category.getDescription(), "Getter for description failed");
	}
}
