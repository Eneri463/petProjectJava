package com.example.petProject.repositories;

import com.example.petProject.models.OrdersProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, Long> {
    List<OrdersProducts> findAllByOrderId(UUID id);
}
