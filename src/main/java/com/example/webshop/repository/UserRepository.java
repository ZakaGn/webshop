package com.example.webshop.repository;

import com.example.webshop.model.User;
import com.example.webshop.model.auth.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT c FROM Credentials c WHERE c.email = ?1")
	Optional<Credentials> findCredentials(String email);
	Optional<User> findUserByCredentialsEmail(String email);
}
