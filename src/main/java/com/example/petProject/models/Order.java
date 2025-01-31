package com.example.petProject.models;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.dto.ProductInListDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "orders")
@Setter
public class Order {

    @Id
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "status")
    boolean status;

    @NotNull
    @Column(name = "order_time")
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OrdersProducts> orderList = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // ------------------------------------------------------------
    // конструкторы

    {
        status = false;
        id = UUID.randomUUID();
        date = LocalDateTime.now();
    }

    public Order() {
    }

    public Order(Customer customer) {
        this.customer = customer;
    }

    public Order(Customer customer, Set<OrdersProducts> orderList) {
        this.customer = customer;
        this.orderList = orderList;
    }

    // ------------------------------------------------------------
    // геттеры

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    public UUID getId() {
        return id;
    }

    public Set<OrdersProducts> getOrderList() {
        return orderList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean getStatus()
    {
        return status;
    }


    // ------------------------------------------------------------
    public void addProduct(Product product, int quantity) {
        OrdersProducts productInList = new OrdersProducts(this, product, quantity);
        orderList.add(productInList);
    }

    public void removeProduct(OrdersProducts partOfOrder) {
        orderList.remove(partOfOrder);
    }

    // ------------------------------------------------------------
    public OrderDTO orderToOrderDTO()
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(this.id);
        orderDTO.setDate(this.getDate());

        Set<ProductInListDTO> products = new HashSet<ProductInListDTO>();

        for(OrdersProducts part : this.orderList)
        {
            Product product = part.getProduct();
            products.add(new ProductInListDTO(product.getId(), product.getName(), part.getQuantity()));
        }

        orderDTO.setOrderList(products);
        orderDTO.setCustomer(this.customer);

        return orderDTO;
    }
}
