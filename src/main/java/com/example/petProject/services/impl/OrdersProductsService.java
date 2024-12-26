package com.example.petProject.services.impl;

import com.example.petProject.models.Customer;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import com.example.petProject.repositories.OrdersProductsRepository;
import com.example.petProject.services.OrdersProductsServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<OrdersProducts> findAllProductsInOrder(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<OrdersProducts> productsInOrders = ordersProductsRepository.findAll(pageable);
        List<OrdersProducts> listOfProducts = productsInOrders.getContent();

        return listOfProducts;
    }

    @Override
    public OrdersProducts saveOrdersProducts(OrdersProducts ordersProduct) {
        return ordersProductsRepository.save(ordersProduct);
    }

    @Override
    public void deleteOrdersProducts(OrdersProductsId id) {
        ordersProductsRepository.deleteById(id);
    }

    @Override
    public OrdersProducts getOrdersProductsById(OrdersProductsId id) {
        return ordersProductsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found in the orders"));
    }

    @Override
    public List<OrdersProducts> getAllProductsInOrderByOrdersId(UUID id) {
        return ordersProductsRepository.findAllByOrderId(id);
    }

    @Override
    public void changeProductQuantity(OrdersProductsId id, int quantity) {
        ordersProductsRepository.setValue(id, quantity);
    }
}
