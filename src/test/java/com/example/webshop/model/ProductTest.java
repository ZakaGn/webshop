package com.example.webshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest{

	@Test
	void testProductEquals(){
		Category category = new Category(1L, "Electronics", null);

		Product product1 = new Product(1L, "Laptop", "A high-performance laptop.", 1200, 10, category);
		Product product2 = new Product(1L, "Laptop", "A high-performance laptop.", 1200, 10, category);

		assertEquals(product1, product2, "Products with the same ID should be equal");
	}

	@Test
	void testProductHashCode(){
		Category category = new Category(1L, "Electronics", null);

		Product product = new Product(1L, "Laptop", "A high-performance laptop.", 1200, 10, category);
		int expectedHashCode = product.getId().hashCode();

		assertEquals(expectedHashCode, product.hashCode(), "Hashcode should be based on product ID");
	}

	@Test
	void testProductSettersAndGetters(){
		Category category = new Category(1L, "Electronics", null);
		Product product = new Product();

		product.setId(1L);
		product.setName("Laptop");
		product.setDescription("A high-performance laptop.");
		product.setPrice(1200);
		product.setQuantity(10);
		product.setCategory(category);

		assertAll("Test Product Getters and Setters", () -> assertEquals(1, product.getId()),
		          () -> assertEquals("Laptop", product.getName()),
		          () -> assertEquals("A high-performance laptop.", product.getDescription()),
		          () -> assertEquals(1200.00, product.getPrice()), () -> assertEquals(10, product.getQuantity()),
		          () -> assertEquals(category, product.getCategory())
		);
	}

}
