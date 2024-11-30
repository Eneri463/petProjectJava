package com.example.petProject.repositories;

import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, OrdersProductsId> {
    List<OrdersProducts> findAllByOrderId(UUID id);

    @Transactional
    @Modifying
    @Query("update OrdersProducts op set op.quantity = :quantity where op.id = :id")
    void setValue(@Param("id") OrdersProductsId id, @Param("quantity") int quantity);
}
