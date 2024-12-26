package com.example.petProject.models.requestBodies;

import com.example.petProject.dto.OrderList;
import com.example.petProject.dto.ProductInformation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Все значения в OrderList заданы корректно")
    void OrderList_IsCorrect(){

        List<ProductInformation> productInformation = List.of(new ProductInformation(1L, 36), new ProductInformation(2L, 36));

        OrderList orderList = new OrderList(1L, productInformation);

        Set<ConstraintViolation<OrderList>> violations = validator.validate(orderList);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id имеет значение null")
    void OrderList_IdIsNull(){

        List<ProductInformation> productInformation = List.of(new ProductInformation(1L, 36), new ProductInformation(2L, 36));

        OrderList orderList = new OrderList(null, productInformation);

        Set<ConstraintViolation<OrderList>> violations = validator.validate(orderList);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id имеет значение меньше минимального")
    void OrderList_IdIsLessThenOne(){

        List<ProductInformation> productInformation = List.of(new ProductInformation(1L, 36), new ProductInformation(2L, 36));

        OrderList orderList = new OrderList(-1L, productInformation);

        Set<ConstraintViolation<OrderList>> violations = validator.validate(orderList);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: список products пуст")
    void OrderList_ProductsListIsNull(){

        OrderList orderList = new OrderList(1L, null);

        Set<ConstraintViolation<OrderList>> violations = validator.validate(orderList);

        assertFalse(violations.isEmpty());
    }
}