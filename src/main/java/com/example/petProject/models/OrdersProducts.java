package com.example.petProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Entity
@Setter
@Table(name = "orders_products")
public class OrdersProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @NotNull
    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    // ------------------------------------------------------------
    // конструкторы

    public OrdersProducts()
    {}

    public OrdersProducts(Order order, Product product, int quantity)
    {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    // ------------------------------------------------------------
    // геттеры

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}