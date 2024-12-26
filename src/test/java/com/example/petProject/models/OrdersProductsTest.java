package com.example.petProject.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrdersProductsTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("OrdersProducts задан корректно")
    void OrdersProducts_IsCorrect()
    {
        Customer customer = new Customer();
        OrdersProducts ordersProducts = new OrdersProducts(new Order(), new Product(), 3);

        Set<ConstraintViolation<OrdersProducts>> violations = validator.validate(ordersProducts);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: order имеет значение null")
    void OrdersProducts_OrderIsNull()
    {
        Customer customer = new Customer();
        OrdersProducts ordersProducts = new OrdersProducts(null, new Product(), 3);

        Set<ConstraintViolation<OrdersProducts>> violations = validator.validate(ordersProducts);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: order имеет значение null")
    void OrdersProducts_ProductIsNull()
    {
        Customer customer = new Customer();
        OrdersProducts ordersProducts = new OrdersProducts(new Order(), null, 3);

        Set<ConstraintViolation<OrdersProducts>> violations = validator.validate(ordersProducts);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: quantity имеет значение меньше минимального")
    void OrdersProducts_QuantityIsLessThenOne()
    {
        Customer customer = new Customer();
        OrdersProducts ordersProducts = new OrdersProducts(new Order(), new Product(), -3);

        Set<ConstraintViolation<OrdersProducts>> violations = validator.validate(ordersProducts);

        assertFalse(violations.isEmpty());
    }

}