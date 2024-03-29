package com.example.webshop.repository;

import com.example.webshop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
	// List<OrderDetail> findByOrderId(Integer orderId);
}
