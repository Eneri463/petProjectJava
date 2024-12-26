package com.example.petProject.models.requestBodies;

import com.example.petProject.dto.ChangeQuantity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChangeQuantityTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Все значения в ChangeQuantity заданы корректно")
    void ChangeQuantity_IsCorrect(){

        ChangeQuantity changeQuantity = new ChangeQuantity(id, 1L, 3);

        Set<ConstraintViolation<ChangeQuantity>> violations = validator.validate(changeQuantity);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: id имеет значение null")
    void ChangeQuantity_IdNameNull(){

        ChangeQuantity changeQuantity = new ChangeQuantity(null, 1L, 3);

        Set<ConstraintViolation<ChangeQuantity>> violations = validator.validate(changeQuantity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: productId имеет значение null")
    void ChangeQuantity_ProductIdNameNull(){
        UUID id = UUID.randomUUID();

        ChangeQuantity changeQuantity = new ChangeQuantity(id, null, 3);

        Set<ConstraintViolation<ChangeQuantity>> violations = validator.validate(changeQuantity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: productId имеет значение меньше единицы")
    void ChangeQuantity_ProductIdNameLessThanOne(){

        ChangeQuantity changeQuantity = new ChangeQuantity(id, -1L, 3);

        Set<ConstraintViolation<ChangeQuantity>> violations = validator.validate(changeQuantity);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: quantity имеет значение меньше единицы")
    void ChangeQuantity_QuantityLessThanOne(){

        ChangeQuantity changeQuantity = new ChangeQuantity(id, 1L, -3);

        Set<ConstraintViolation<ChangeQuantity>> violations = validator.validate(changeQuantity);

        assertFalse(violations.isEmpty());
    }

}