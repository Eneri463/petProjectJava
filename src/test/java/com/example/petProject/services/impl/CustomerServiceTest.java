package com.example.petProject.services.impl;

import com.example.petProject.models.Customer;
import com.example.petProject.repositories.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService service;

    @Test
    @DisplayName("findAllCustomers возвращает из БД список всех покупателей")
    void findAllCustomers_ReturnValidResponseEntity()
    {
        // given
        List<Customer> customers = List.of(new Customer(1L, "Валерий", "Меладзе",
                        "88888888888", "Популярная", "30", 1),
                new Customer(2L, "Альбина", "Джанабаева",
                        "77777777777", "Популярная", "30", 1));

        Pageable pageable = PageRequest.of(0, 10);

        Page<Customer> pages = new PageImpl<>(customers, pageable, 0);

        Mockito.doReturn(pages).when(this.customerRepository).findAll(Mockito.any(Pageable.class));

        // when
        var response = this.service.getAllCustomers(0, 10);

        // then
        assertNotNull(response);
        assertEquals(customers,response);

    }

    @Test
    @DisplayName("getCustomerById возвращает из БД покупателя с указанным id")
    void getCustomerById_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;
        Customer customer = new Customer(id, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Mockito.when(this.customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // when
        var response = this.service.getCustomerById(id);

        // then
        assertNotNull(response);
        assertEquals(customer,response);
    }

    @Test
    @DisplayName("deleteCustomer инициирует удаление из БД покупателя с указанным id")
    void deleteCustomer_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;

        // when
        this.service.deleteCustomer(id);

        // then
        Mockito.verify(this.customerRepository).deleteById(id);
    }

    @Test
    @DisplayName("saveCustomer инициирует сохранение в БД указанного покупателя")
    void saveCustomer_ReturnValidResponseEntity()
    {
        // given
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Mockito.when(this.customerRepository.save(customer)).thenReturn(customer);

        // when
        var response = this.service.saveCustomer(customer);

        // then
        assertNotNull(response);
        assertEquals(customer,response);
    }

}