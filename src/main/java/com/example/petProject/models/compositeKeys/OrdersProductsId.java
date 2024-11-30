package com.example.petProject.models.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrdersProductsId implements Serializable {

    @Column(name = "product_id")
    private Long productId;
    @Column(name = "order_id")
    private UUID orderId;

    public OrdersProductsId() {

    }

    public OrdersProductsId(Long productId, UUID orderId) {
        super();
        this.productId = productId;
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result
                + ((orderId == null) ? 0 : orderId.hashCode());
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
        OrdersProductsId other = (OrdersProductsId) obj;
        return Objects.equals(getProductId(), other.getProductId()) && Objects.equals(getOrderId(), other.getOrderId());
    }
}
