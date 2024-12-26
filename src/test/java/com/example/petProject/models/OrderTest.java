package com.example.petProject.models;

import com.example.petProject.controllers.CustomerController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Все значения в Order заданы корректно")
    void Order_IsCorrect(){

        Set<OrdersProducts> orderList = new HashSet<>();
        Order order = new Order(new Customer(), orderList);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: customer это null")
    void Order_CustomerIsNull(){

        Set<OrdersProducts> orderList = new HashSet<>();
        Order order = new Order(null, orderList);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty());
    }

}