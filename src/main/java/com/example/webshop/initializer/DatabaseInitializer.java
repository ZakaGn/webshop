package com.example.webshop.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer implements CommandLineRunner{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public void run(String... args) throws Exception{
		//if(areAllTablesEmpty()) insertInitialData();
	}

	private boolean areAllTablesEmpty(){
		Integer usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
		Integer credentialsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM credentials", Integer.class);
		Integer categoriesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM categories", Integer.class);
		Integer productsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
		Integer ordersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
		Integer orderDetailsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM order_details", Integer.class);
		Integer cartCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cart", Integer.class);
		Integer cartItemsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cart_items", Integer.class);

		return usersCount + credentialsCount + categoriesCount + productsCount + ordersCount + orderDetailsCount + cartCount + cartItemsCount == 0;
	}

	private void insertInitialData(){
		jdbcTemplate.update("INSERT INTO users (first_name, last_name) VALUES (?, ?)", "John", "Doe");
		jdbcTemplate.update("INSERT INTO users (first_name, last_name) VALUES (?, ?)", "Jane", "Doe");

		jdbcTemplate.update("INSERT INTO credentials (email, password, role, user_id) VALUES (?, ?, ?, ?)",
		                    "john.doe@example.com", "hashed_password", "USER", 1
		);
		jdbcTemplate.update("INSERT INTO credentials (email, password, role, user_id) VALUES (?, ?, ?, ?)",
		                    "jane.doe@example.com", "hashed_password", "ADMIN", 2
		);

		jdbcTemplate.update("INSERT INTO categories (name) VALUES (?)", "Category 1");
		jdbcTemplate.update("INSERT INTO categories (name) VALUES (?)", "Category 2");

		jdbcTemplate.update("INSERT INTO products (name, price, category_id) VALUES (?, ?, ?)", "Product 1", 100, 1);
		jdbcTemplate.update("INSERT INTO products (name, price, category_id) VALUES (?, ?, ?)", "Product 2", 200, 2);

		jdbcTemplate.update("INSERT INTO orders (user_id) VALUES (?)", 1);
		jdbcTemplate.update("INSERT INTO orders (user_id) VALUES (?)", 2);

		jdbcTemplate.update("INSERT INTO order_details (order_id, product_id, quantity) VALUES (?, ?, ?)", 1, 1, 1);
		jdbcTemplate.update("INSERT INTO order_details (order_id, product_id, quantity) VALUES (?, ?, ?)", 2, 2, 2);

		jdbcTemplate.update("INSERT INTO cart (user_id) VALUES (?)", 1);
		jdbcTemplate.update("INSERT INTO cart (user_id) VALUES (?)", 2);

		jdbcTemplate.update("INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)", 1, 1, 1);
		jdbcTemplate.update("INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)", 2, 2, 2);

	}

}
