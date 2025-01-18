package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Product;
import com.example.petProject.repositories.CustomerRepository;
import com.example.petProject.repositories.OrdersProductsRepository;
import com.example.petProject.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class ProductControllerTestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrdersProductsRepository ordersProductsRepository;

    @BeforeAll
    void beforeAll()
    {
        ordersProductsRepository.deleteAll();
        productRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        this.productRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /products возвращает HTTP-ответ со статусом 200 OK и список всех продуктов")
    void allProducts_ReturnValidResponseEntity() throws Exception{
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/products");

        this.productRepository.save(new Product("молоко", 85));

        this.productRepository.save(new Product("хлеб", 55));


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                """
                                        [
                                            {
                                                "name": "молоко",
                                                "price": 85
                                            },
                                            {
                                                "name": "хлеб",
                                                "price": 55
                                            }
                                        ]
                                        """
                        )
                );

    }

    @Test
    @DisplayName("GET /product/{id} возвращает HTTP-ответ со статусом 200 OK и продукт с указанным id")
    void findProductById_ReturnValidResponseEntity() throws Exception{
        // given
        Product product = this.productRepository.save(new Product("молоко", 85));

        var requestBuilder = MockMvcRequestBuilders.get("/product/"+product.getId());

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                """
                                            {
                                                "name": "молоко",
                                                "price": 85
                                            }
                                        """
                        )
                );

    }

    @Test
    @DisplayName("DELETE /product/delete возвращает HTTP-ответ со статусом 200 OK и удаляет продукт из БД")
    void deleteProduct_ReturnValidResponseEntity() throws Exception{
        // given
        Product product = this.productRepository.save(new Product("молоко", 85));

        var requestBuilder = MockMvcRequestBuilders.delete("/product/delete")
                .param("id", product.getId().toString());


        // when
        this.mockMvc.perform(requestBuilder).
                //then
                        andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(0, this.productRepository.findAll().size());

    }

    @Test
    @DisplayName("POST /product/create возвращает HTTP-ответ со статусом CREATED и созданный продукт")
    void createProduct_ReturnValidResponseEntity() throws Exception{
        // given

        var requestBuilder = MockMvcRequestBuilders.post("/product/create").contentType(MediaType.APPLICATION_JSON)
                .content("""
                                            {
                                                "name": "молоко",
                                                "price": 85
                                            }
                                            """);;


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.content().json(
                                """
                                            {
                                                "name": "молоко",
                                                "price": 85
                                            }
                                        """
                        )
                );

    }

}