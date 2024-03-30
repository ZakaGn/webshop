package com.example.webshop.repository;

import com.example.webshop.model.Cart;
import com.example.webshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Optional<Cart> findByUserId(Long userId);

	@Query("SELECT ci FROM CartItem ci JOIN ci.cart c WHERE ci.id = :cartItemId")
	Optional<CartItem> findCartItemById(@Param("cartItemId") Long cartItemId);
}
