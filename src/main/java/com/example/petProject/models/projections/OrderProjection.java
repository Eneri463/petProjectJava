package com.example.petProject.models.projections;

import com.example.petProject.models.Customer;
import java.util.Set;
import java.util.UUID;

public interface OrderProjection {

    UUID getId();
    String getDate();

    Set<OrdersProductsProjection> getOrderList();

    Customer getCustomer();

    interface OrdersProductsProjection
    {
        ProductProjection getProduct();

        interface ProductProjection
        {
            Long getId();
            String getName();
            Double getPrice();
        }
    }

}
