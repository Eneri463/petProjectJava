package com.example.petProject.models.requestBodies;

import com.example.petProject.dto.ProductInformation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductInformationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("ProductInformation задан корректно")
    void ProductInformation_IsCorrect()
    {
        ProductInformation productInformation = new ProductInformation(1L, 64);

        Set<ConstraintViolation<ProductInformation>> violations = validator.validate(productInformation);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: productId это null")
    void ProductInformation_ProductIdIsNull()
    {
        ProductInformation  productInformation = new ProductInformation(null, 64);

        Set<ConstraintViolation<ProductInformation>> violations = validator.validate(productInformation);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: productId меньше минимального значения")
    void ProductInformation_ProductIdIsLessThenOne()
    {
        ProductInformation  productInformation = new ProductInformation(-1L, 64);

        Set<ConstraintViolation<ProductInformation>> violations = validator.validate(productInformation);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: quantity меньше минимального значения")
    void ProductInformation_QuantityIsLessThenOne()
    {
        ProductInformation  productInformation = new ProductInformation(1L, -1);

        Set<ConstraintViolation<ProductInformation>> violations = validator.validate(productInformation);

        assertFalse(violations.isEmpty());
    }


}