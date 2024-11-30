package com.example.petProject.services;

import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.compositeKeys.OrdersProductsId;

import java.util.List;
import java.util.UUID;

public interface OrdersProductsServiceInterface {
    List<OrdersProducts> findAllProducts();

    void saveOrdersProducts(OrdersProducts ordersProduct);

    void deleteOrdersProducts(OrdersProductsId id);

    OrdersProducts getOrdersProductsById(OrdersProductsId id);

    List<OrdersProducts> getAllProductsInOrderByOrdersId(UUID id);

    public void changeProductQuantity(OrdersProductsId id, int quantity);
}
