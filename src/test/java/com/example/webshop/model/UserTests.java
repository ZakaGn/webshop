package com.example.webshop.model;

import com.example.webshop.model.User;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests{

	@Test
	public void testUserGettersAndSetters(){
		User user = new User();
		Long expectedId = 1L;
		String expectedUsername = "testUser";
		String expectedPassword = "password";
		Boolean expectedEnabled = true;
		String expectedEmail = "testUser@example.com";
		Timestamp expectedCreatedAt = new Timestamp(System.currentTimeMillis());

		user.setId(expectedId);
		user.setUsername(expectedUsername);
		user.setPassword(expectedPassword);
		user.setEnabled(expectedEnabled);
		user.setEmail(expectedEmail);
		user.setCreatedAt(expectedCreatedAt);

		assertEquals(expectedId, user.getId());
		assertEquals(expectedUsername, user.getUsername());
		assertEquals(expectedPassword, user.getPassword());
		assertEquals(expectedEnabled, user.getEnabled());
		assertEquals(expectedEmail, user.getEmail());
		assertEquals(expectedCreatedAt, user.getCreatedAt());
	}

}
