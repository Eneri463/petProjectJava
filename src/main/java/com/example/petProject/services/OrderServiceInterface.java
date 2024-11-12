package com.example.petProject.services;

import com.example.petProject.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {

    List<Order> findAllOrders();

    List<Order> findOrdersByUserId(Long id);

    void saveOrder(Order order);

    void deleteOrder(UUID id);

    Order getOrderById(UUID id);

}
