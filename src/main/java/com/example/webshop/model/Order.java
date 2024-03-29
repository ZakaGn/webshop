package com.example.webshop.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<OrderDetail> orderDetails = new HashSet<>();

	@Column(nullable = false)
	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	public void addOrderDetail(OrderDetail orderDetail){
		orderDetails.add(orderDetail);
		orderDetail.setOrder(this);
	}

	public void removeOrderDetail(OrderDetail orderDetail){
		orderDetails.remove(orderDetail);
		orderDetail.setOrder(null);
	}

	@PrePersist
	protected void onCreate(){
		orderDate = LocalDateTime.now();
		status = OrderStatus.PENDING;
	}

}
