package com.example.petProject.services.impl;

import com.example.petProject.models.Order;
import com.example.petProject.models.projections.OrderProjection;
import com.example.petProject.repositories.OrderRepository;
import com.example.petProject.services.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final OrderRepository orderRepository;
    @Override
    public List<OrderProjection> findAllOrders() {
        return orderRepository.findAllOrder();
    }

    @Override
    public List<OrderProjection> findOrdersByUserId(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderProjection getOrderProjectionById(UUID id){

        List<OrderProjection> res = orderRepository.findViewById(id);

        if (res.size() != 0)
        {
            return res.get(0);
        }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    }

    @Override
    public Order getOrderById(UUID id) {

        return orderRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Override
    public void changeOrdersStatus(UUID id) {
        orderRepository.setValue(id);
    }
}
