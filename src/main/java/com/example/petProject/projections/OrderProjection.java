package com.example.petProject.projections;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Product;

import java.util.Set;
import java.util.UUID;

public interface OrderProjection {

    UUID getId();
    String getDate();

    Set<OrdersProductsProjection> getOrderList();

    interface OrdersProductsProjection
    {
        Product getProduct();
    }

    Customer getCustomer();
}
