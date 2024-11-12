package com.example.petProject.services;

import com.example.petProject.models.OrdersProducts;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsServiceInterface {
    List<OrdersProducts> findAllProducts();

    void saveOrdersProducts(OrdersProducts ordersProduct);

    void deleteOrdersProducts(Long id);

    OrdersProducts getOrdersProductsById(Long id);

    List<OrdersProducts> getAllProductsInOrderByOrdersId(UUID id);
}
