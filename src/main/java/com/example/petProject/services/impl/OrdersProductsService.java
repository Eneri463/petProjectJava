package com.example.petProject.services.impl;

import com.example.petProject.models.OrdersProducts;
import com.example.petProject.repositories.OrdersProductsRepository;
import com.example.petProject.services.OrdersProductsServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersProductsService implements OrdersProductsServiceInterface {

    private final OrdersProductsRepository ordersProductsRepository;

    @Override
    public List<OrdersProducts> findAllProducts() {
        return ordersProductsRepository.findAll();
    }

    @Override
    public void saveOrdersProducts(OrdersProducts ordersProduct) {
        ordersProductsRepository.save(ordersProduct);
    }

    @Override
    public void deleteOrdersProducts(Long id) {
        ordersProductsRepository.deleteById(id);
    }

    @Override
    public OrdersProducts getOrdersProductsById(Long id) {
        return ordersProductsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found in the orders"));
    }

    @Override
    public List<OrdersProducts> getAllProductsInOrderByOrdersId(UUID id) {
        return ordersProductsRepository.findAllByOrderId(id);
    }
}
