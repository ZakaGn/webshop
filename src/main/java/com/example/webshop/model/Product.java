package com.example.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column
	private String description;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof Product product)) return false;
		return id.equals(product.id);
	}

	@Override
	public int hashCode(){
		return id.hashCode();
	}

}
