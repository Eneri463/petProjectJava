package com.example.petProject.services;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {

    List<OrderDTO> findAllOrders(int pageNo, int pageSize);

    List<OrderDTO> findOrdersByUserId(Long id, int pageNo, int pageSize);

    OrderDTO saveOrder(Order order);

    void deleteOrder(UUID id);

    OrderDTO getOrderDTOById(UUID id);

    Order getOrderById(UUID id);

    void changeOrdersStatus(UUID id);

}
