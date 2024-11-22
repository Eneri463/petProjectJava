package com.example.petProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//todo
// может, стоит сделать какой-то конструктор, который посчитает totalCost

@Entity
@Table(name = "orders")
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
    // сеттеры

    public void setCustomerId(Customer customer) {
        this.customer = customer;
    }

    public void setOrderList(Set<OrdersProducts> orderList) {
        this.orderList = orderList;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
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

    // ------------------------------------------------------------
    public void addProduct(Product product, int quantity) {
        OrdersProducts productInList = new OrdersProducts(this, product, quantity);
        orderList.add(productInList);
    }

    public void removeProduct(OrdersProducts partOfOrder) {
        orderList.remove(partOfOrder);
    }

}
