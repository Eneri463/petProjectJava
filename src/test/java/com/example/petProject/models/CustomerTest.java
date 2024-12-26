package com.example.petProject.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Все значения в Customer заданы корректно")
    void Customer_IsCorrect(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                        "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: значение id меньше минимального значения")
    void Customer_IdLessThenOne(){
        Customer customer = new Customer(-1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: firstName это пустая строка")
    void Customer_FirstNameEmpty(){
        Customer customer = new Customer(1L, "", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: firstName имеет значение null")
    void Customer_FirstNameNull(){
        Customer customer = new Customer(1L, null, "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: firstName слишком длинное")
    void Customer_FirstNameTooLong(){
        Customer customer = new Customer(1L, "Валерийабвгдеёжзиклмнопрст", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: firstName задано некорректно")
    void Customer_FirstNameIncorrectSymbols(){
        Customer customer1 = new Customer(1L, "Valeriy", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        Customer customer2 = new Customer(1L, "вАлерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        Customer customer3 = new Customer(1L, "Валер1ий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations1 = validator.validate(customer1);
        Set<ConstraintViolation<Customer>> violations2 = validator.validate(customer2);
        Set<ConstraintViolation<Customer>> violations3 = validator.validate(customer3);

        assertFalse(violations1.isEmpty());
        assertFalse(violations2.isEmpty());
        assertFalse(violations3.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: lastName это пустая строка")
    void Customer_LastNameEmpty(){
        Customer customer = new Customer(1L, "Валерий", "",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: lastName имеет значение null")
    void Customer_LastNameNull(){
        Customer customer = new Customer(1L, "Валерий", null,
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: lastName слишком длинное")
    void Customer_LastNameTooLong(){
        Customer customer = new Customer(1L, "Валерий", "Меладзеабвгдеёжзиклмно",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: lastName задано некорректно")
    void Customer_LastNameIncorrectSymbols(){
        Customer customer1 = new Customer(1L, "Валерий", "Meladze",
                "88888888888", "Популярная", "30", 1);
        Customer customer2 = new Customer(1L, "Валерий", "мЕладзе",
                "88888888888", "Популярная", "30", 1);
        Customer customer3 = new Customer(1L, "Валерий", "Меладз1е",
                "88888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations1 = validator.validate(customer1);
        Set<ConstraintViolation<Customer>> violations2 = validator.validate(customer2);
        Set<ConstraintViolation<Customer>> violations3 = validator.validate(customer3);

        assertFalse(violations1.isEmpty());
        assertFalse(violations2.isEmpty());
        assertFalse(violations3.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: phoneNumber это пустая строка")
    void Customer_PhoneNumberEmpty(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: phoneNumber имеет значение null")
    void Customer_PhoneNumberNull(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                null, "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: phoneNumber некорректной длины")
    void Customer_PhoneNumberTooLong(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "8888888888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: phoneNumber задано некорректно")
    void Customer_PhoneNumberIncorrectSymbols(){
        Customer customer1 = new Customer(1L, "Валерий", "Меладзе",
                "98888888888", "Популярная", "30", 1);
        Customer customer2 = new Customer(1L, "Валерий", "Меладзе",
                "8-8888888888", "Популярная", "30", 1);

        Set<ConstraintViolation<Customer>> violations1 = validator.validate(customer1);
        Set<ConstraintViolation<Customer>> violations2 = validator.validate(customer2);

        assertFalse(violations1.isEmpty());
        assertFalse(violations2.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: street это пустая строка")
    void Customer_StreetEmpty(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: street имеет значение null")
    void Customer_StreetNull(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", null, "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: street некорректной длины")
    void Customer_StreetTooLong(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная очень улица длина большая у неё", "30", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: street задано некорректно")
    void Customer_StreetIncorrectSymbols(){
        Customer customer1 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "-Популярная", "30", 1);
        Customer customer2 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная, но", "30", 1);

        Set<ConstraintViolation<Customer>> violations1 = validator.validate(customer1);
        Set<ConstraintViolation<Customer>> violations2 = validator.validate(customer2);

        assertFalse(violations1.isEmpty());
        assertFalse(violations2.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: house это пустая строка")
    void Customer_HouseEmpty(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: house имеет значение null")
    void Customer_HouseNull(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", null, 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: house некорректной длины")
    void Customer_HouseTooLong(){
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30111112", 1);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Ошибка: house задано некорректно")
    void Customer_HouseIncorrectSymbols(){
        Customer customer1 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30/1a", 1);
        Customer customer2 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30aa", 1);

        Set<ConstraintViolation<Customer>> violations1 = validator.validate(customer1);
        Set<ConstraintViolation<Customer>> violations2 = validator.validate(customer2);

        assertFalse(violations1.isEmpty());
        assertFalse(violations2.isEmpty());
    }

}