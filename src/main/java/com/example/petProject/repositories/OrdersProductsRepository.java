package com.example.petProject.repositories;

import com.example.petProject.models.OrdersProducts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, Long> {
    List<OrdersProducts> findAllByOrderId(UUID id);

    @Transactional
    @Modifying
    @Query("update OrdersProducts set quantity = :quantity where id = :id")
    void setValue(@Param("id") Long id, @Param("quantity") int quantity);
}
