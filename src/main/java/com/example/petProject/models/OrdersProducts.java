package com.example.petProject.models;

import com.example.petProject.models.compositeKeys.OrdersProductsId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Table(name = "orders_products")
@NoArgsConstructor
public class OrdersProducts {

    @EmbeddedId
    OrdersProductsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId("orderId")
    @NotNull
    private Order order;

    @NotNull
    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    // ------------------------------------------------------------
    // конструкторы

    public OrdersProducts(Order order, Product product, int quantity)
    {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        if (order == null || product == null) return;
        this.id = new OrdersProductsId(product.getId(), order.getId());
    }

    // ------------------------------------------------------------
    // геттеры

    public OrdersProductsId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {return  quantity;}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrdersProducts other = (OrdersProducts) obj;
        return Objects.equals(getId(), other.getId());
    }

}