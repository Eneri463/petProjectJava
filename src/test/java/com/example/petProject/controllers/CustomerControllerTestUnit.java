package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.services.CustomerServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTestUnit {

    @Mock
    CustomerServiceInterface customerService;

    @InjectMocks
    CustomerController controller;

    @Test
    @DisplayName("GET /customers возвращает HTTP-ответ со статусом 200 OK и список всех покупателей")
    void allCustomers_ReturnValidResponseEntity()
    {
        // given
        List<Customer> customers = List.of(new Customer(1L, "Валерий", "Меладзе",
                            "88888888888", "Популярная", "30", 1),
                                        new Customer(2L, "Леонид", "Каневский",
                            "77777777777", "Загадочная", "15а", 1));


        Mockito.doReturn(customers).when(this.customerService).getAllCustomers(0,10);

        // when
        var response = this.controller.allCustomers(1, 10);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers,response.getBody());

    }

    @Test
    @DisplayName("GET /customer/{id} возвращает HTTP-ответ со статусом 200 OK и информацией о покупателе с указанным id")
    void findCustomerById_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;
        Customer customer = new Customer(id, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Mockito.when(customerService.getCustomerById(id)).thenReturn(customer);

        // when
        var response = this.controller.findCustomerById(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer,response.getBody());

    }

    @Test
    @DisplayName("DELETE /customer возвращает HTTP-ответ со статусом 200 OK")
    void deleteCustomer_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;

        // when
        var response = this.controller.deleteCustomer(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The customer has been removed", response.getBody());
        Mockito.verify(this.customerService).deleteCustomer(id);
    }

    @Test
    @DisplayName("POST /customer возвращает HTTP-ответ со статусом 200 OK")
    void createCustomer_ReturnValidResponseEntity()
    {
        // given
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Mockito.when(customerService.saveCustomer(customer)).thenReturn(customer);

        // when
        var response = this.controller.createCustomer(customer);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

}