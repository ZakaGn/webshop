package com.example.webshop.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cart")
public class Cart{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<CartItem> cartItems = new HashSet<>();

	public void addCartItem(CartItem cartItem){
		cartItems.add(cartItem);
		cartItem.setCart(this);
	}

	public void removeCartItem(CartItem cartItem){
		cartItems.remove(cartItem);
		cartItem.setCart(null);
	}

	@PrePersist
	protected void onCreate(){
		createdAt = LocalDateTime.now();
	}
}
