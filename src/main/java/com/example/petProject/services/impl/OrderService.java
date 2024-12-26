package com.example.petProject.services.impl;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Order;
import com.example.petProject.repositories.OrderRepository;
import com.example.petProject.services.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final OrderRepository orderRepository;
    @Override
    public List<OrderDTO> findAllOrders(int pageNo, int pageSize) {

        List<OrderDTO> listOfOrders = new ArrayList<>();

        for (Order order : orderRepository.findAllOrderIn(pageNo*pageSize,pageSize))
        {
            listOfOrders.add(order.orderToOrderDTO());
        }

        return listOfOrders;
    }

    @Override
    public List<OrderDTO> findOrdersByUserId(Long id, int pageNo, int pageSize) {

        List<OrderDTO> listOfOrders = new ArrayList<>();

        for (Order order : orderRepository.findByUserId(id, pageNo*pageSize,pageSize))
        {
            listOfOrders.add(order.orderToOrderDTO());
        }

        return listOfOrders;
    }

    @Override
    public OrderDTO saveOrder(Order order) {
        return orderRepository.save(order).orderToOrderDTO();
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO getOrderDTOById(UUID id){

        List<Order> order =  orderRepository.getByIdNotProxy(id);

        if (order.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");

        return order.get(0).orderToOrderDTO();
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
