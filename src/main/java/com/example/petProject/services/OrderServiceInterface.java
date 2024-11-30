package com.example.petProject.services;

import com.example.petProject.models.Order;
import com.example.petProject.models.projections.OrderProjection;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {

    List<OrderProjection> findAllOrders();

    List<OrderProjection> findOrdersByUserId(Long id);

    void saveOrder(Order order);

    void deleteOrder(UUID id);

    OrderProjection getOrderProjectionById(UUID id);

    Order getOrderById(UUID id);

    void changeOrdersStatus(UUID id);

}
