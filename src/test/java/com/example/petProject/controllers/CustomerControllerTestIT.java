package com.example.petProject.controllers;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Customer;
import com.example.petProject.repositories.CustomerRepository;
import com.example.petProject.repositories.OrderRepository;
import com.example.petProject.repositories.OrdersProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
class CustomerControllerTestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrdersProductsRepository ordersProductsRepository;

    ObjectMapper objectMapper;
    Customer customer1;
    Customer customer2;

    @BeforeAll
    void beforeAll()
    {
        this.ordersProductsRepository.deleteAll();
        this.orderRepository.deleteAll();
        this.customerRepository.deleteAll();

        this.objectMapper = new ObjectMapper();

        this.customer1 = new Customer("Валерий", "Меладзе",
                "77777777777", "Популярная", "120", 1);

        this.customer2 = new Customer("Леонид", "Каневский",
                "71111111111", "Загадочная", "42", 42);
    }

    @AfterEach
    void afterEach() {
        this.customerRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /customers возвращает HTTP-ответ со статусом 200 OK и список всех покупателей")
    void allCustomers_ReturnValidResponseEntity() throws Exception{
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/customers");

        this.customerRepository.save(customer1);
        this.customerRepository.save(customer2);

        List<Customer> list = List.of(customer1, customer2);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(list))
                );

    }

    @Test
    @DisplayName("GET /customer/{id} возвращает HTTP-ответ со статусом 200 OK и покупателя с указанным id")
    void findCustomerById_ReturnValidResponseEntity() throws Exception{
        // given
        Customer customer = this.customerRepository.save(customer1);

        this.customerRepository.save(customer2);

        var requestBuilder = MockMvcRequestBuilders.get("/customer/"+customer.getId());

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(customer))
                );

    }

    @Test
    @DisplayName("DELETE /customer/delete возвращает HTTP-ответ со статусом 200 OK и удаляет указанного пользователя")
    void deleteCustomer_ReturnValidResponseEntity() throws Exception{
        // given
        Customer customer = this.customerRepository.save(customer1);

        var requestBuilder = MockMvcRequestBuilders.delete("/customer/delete")
                .param("id", customer.getId().toString());


        // when
        this.mockMvc.perform(requestBuilder).
                //then
                andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(0, this.customerRepository.findAll().size());

    }

    @Test
    @DisplayName("POST /customer/create возвращает HTTP-ответ со статусом CREATE и созданного покупателя")
    void createCustomer_ReturnValidResponseEntity() throws Exception{
        // given

        var requestBuilder = MockMvcRequestBuilders.post("/customer/create").contentType(MediaType.APPLICATION_JSON)
                                        .content(this.objectMapper.writeValueAsString(customer1));


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.jsonPath("$.firstName").value("Валерий"),
                        MockMvcResultMatchers.jsonPath("$.lastName").value("Меладзе"),
                        MockMvcResultMatchers.jsonPath("$.phoneNumber").value("77777777777"),
                        MockMvcResultMatchers.jsonPath("$.street").value("Популярная"),
                        MockMvcResultMatchers.jsonPath("$.house").value("120"),
                        MockMvcResultMatchers.jsonPath("$.apartment").value("1")

                        );

    }

}