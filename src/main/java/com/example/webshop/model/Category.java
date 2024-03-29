package com.example.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column
	private String description;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Product> products;

	public Category(Long id, String name, String description){
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof Category category)) return false;
		return id != null && id.equals(category.getId());
	}

	@Override
	public int hashCode(){
		return id != null ? id.hashCode() : 0;
	}

}
