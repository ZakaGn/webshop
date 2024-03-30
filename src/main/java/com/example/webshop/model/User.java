package com.example.webshop.model;

import com.example.webshop.model.auth.Credentials;
import com.example.webshop.model.auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "credentials_id", referencedColumnName = "id")
	private Credentials credentials;

	@NotBlank(message = "First name is required")
	@Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
	@Column(name = "first_name" , nullable = false)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
	@Column(name = "last_name" , nullable = false)
	private String lastName;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cart cart;

	public String getEmail(){
		return this.credentials.getEmail();
	}

	public String getPassword(){
		return this.credentials.getPassword();
	}

	public Role getRole(){
		return this.credentials.getRole();
	}

	public void setEmail(String email){
		this.credentials.setEmail(email);
	}

	public void setRole(Role role){
		this.credentials.setRole(role);
	}

	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.credentials.getAuthorities();
	}

	@PrePersist
	protected void onCreate(){
		Cart cart = new Cart();
		cart.setUser(this);
		this.cart = cart;
	}

}
