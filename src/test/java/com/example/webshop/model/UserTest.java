package com.example.webshop.model;

import com.example.webshop.model.auth.Credentials;
import com.example.webshop.model.auth.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest{

	@Test
	public void testUserDelegatesToCredentials(){
		Credentials credentials = Credentials
			.builder()
			.email("user@example.com")
			.password("password")
			.role(Role.CLIENT)
			.build();

		User user = new User();
		user.setCredentials(credentials);

		assertEquals(
			"user@example.com",
			user.getEmail(),
			"The email should match the one set in Credentials."
		);
		assertEquals(
			"password",
			user.getPassword(),
			"The password should match the one set in Credentials."
		);
		assertEquals(
			Role.CLIENT,
			user.getRole(),
			"The role should match the one set in Credentials."
		);

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		assertEquals(
			1,
			authorities.size(),
			"There should be exactly one authority."
		);
		assertEquals(
			Role.CLIENT,
			user.getRole(),
     "The authority should match the user's role."
		);
	}

	@Test
	public void testEmailAndPasswordModificationThroughUser(){
		Credentials credentials = Credentials.builder().build();
		User user = new User();
		user.setCredentials(credentials);

		user.setEmail("newemail@example.com");
		user.setRole(Role.MANAGER);

		assertEquals("newemail@example.com", user.getEmail(), "Email should be updated through User.");
		assertEquals(Role.MANAGER, user.getRole(), "Role should be updated through User.");
	}

}
