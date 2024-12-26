package com.example.petProject.services;

import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.compositeKeys.OrdersProductsId;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsServiceInterface {
    List<OrdersProducts> findAllProductsInOrder(int pageNo, int pageSize);

    OrdersProducts saveOrdersProducts(OrdersProducts ordersProduct);

    void deleteOrdersProducts(OrdersProductsId id);

    OrdersProducts getOrdersProductsById(OrdersProductsId id);

    List<OrdersProducts> getAllProductsInOrderByOrdersId(UUID id);

    void changeProductQuantity(OrdersProductsId id, int quantity);
}
