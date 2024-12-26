package com.example.petProject.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Product задан корректно")
    void Product_IsCorrect()
    {
        Product product = new Product(1L, "молоко", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id это null")
    void Product_IdIsNull()
    {
        Product product = new Product(null, "молоко", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id меньше минимального")
    void Product_IdIsLessThenOne()
    {
        Product product = new Product(-1L, "молоко", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: name это пустая строка")
    void Product_NameEmpty(){
        Product product = new Product(1L, "", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: name имеет значение null")
    void Product_NameNull(){
        Product product = new Product(1L, null, 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: name некорректной длины")
    void Product_NameTooLong(){
        Product product = new Product(1L, "Популярный очень продукт название большое у него", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: name задано некорректно")
    void Product_NameIncorrectSymbols(){
        Product product = new Product(1L, "-Популярный", 36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id меньше минимального")
    void Product_PriceIsLessThenZero()
    {
        Product product = new Product(1L, "молоко", -36);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());
    }

}