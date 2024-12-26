package com.example.petProject.controllers;

import com.example.petProject.dto.ChangeQuantity;
import com.example.petProject.dto.OrderDTO;
import com.example.petProject.dto.OrderList;
import com.example.petProject.dto.ProductInformation;
import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.repositories.CustomerRepository;
import com.example.petProject.repositories.OrderRepository;
import com.example.petProject.repositories.OrdersProductsRepository;
import com.example.petProject.repositories.ProductRepository;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
class OrdersControllerTestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrdersProductsRepository ordersProductsRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    ObjectMapper objectMapper;
    Customer customer1;
    Customer customer2;
    Product product1;
    Product product2;

    @BeforeAll
    public void beforeAll()
    {
        this.objectMapper = new ObjectMapper();

        this.ordersProductsRepository.deleteAll();
        this.orderRepository.deleteAll();

        this.customerRepository.deleteAll();
        this.customer1 = this.customerRepository.save(new Customer("Валерий", "Меладзе",
                "77777777777", "Популярная", "120", 1));

        this.customer2 = this.customerRepository.save(new Customer("Леонид", "Каневский",
                "71111111111", "Загадочная", "42", 42));

        this.productRepository.deleteAll();
        this.product1 = this.productRepository.save(new Product("молоко", 85));
        this.product2 = this.productRepository.save(new Product("хлеб", 55));

    }

    @AfterEach
    void afterEach() {
        this.orderRepository.deleteAll();
        this.ordersProductsRepository.deleteAll();
    }

    @AfterAll
    void afterAll()
    {
        this.customerRepository.deleteAll();
        this.productRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /orders возвращает HTTP-ответ со статусом 200 OK и список всех заказов")
    void allOrders_ReturnValidResponseEntity() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        Order order2 = new Order(customer2);
        order2.addProduct(product2, 2);

        this.orderRepository.save(order);
        this.orderRepository.save(order2);

        var requestBuilder = MockMvcRequestBuilders.get("/orders");

        List<OrderDTO> list = List.of(order.orderToOrderDTO(),order2.orderToOrderDTO());

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                this.objectMapper.writeValueAsString(list)
                        )
                );

    }

    @Test
    @DisplayName("GET /order возвращает HTTP-ответ со статусом 200 OK и заказ с указанным id")
    void findOrderById_ReturnValidResponseEntity() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        this.orderRepository.save(order);

        var requestBuilder = MockMvcRequestBuilders.get("/order")
                .param("id",order.getId().toString());

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(order.orderToOrderDTO()))
                );
    }

    @Test
    @DisplayName("GET /order/customer/{id} возвращает HTTP-ответ со статусом 200 OK и заказы пользователя с указанным id")
    void findOrderByUserId_ReturnValidResponseEntity() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        Order order2 = new Order(customer1);
        order2.addProduct(product2, 5);

        Order order3 = new Order(customer2);
        order2.addProduct(product2, 2);

        this.orderRepository.save(order);
        this.orderRepository.save(order2);
        this.orderRepository.save(order3);

        List<OrderDTO> list = List.of(order.orderToOrderDTO(),order2.orderToOrderDTO());

        var requestBuilder = MockMvcRequestBuilders.get("/order/customer/" + order.getCustomer().getId());

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(list))
                );
    }

    @Test
    @DisplayName("POST /order/create возвращает HTTP-ответ со статусом CREATED и возвращает созданный заказ")
    void createOrder_ReturnValidResponseEntity() throws Exception
    {
        // given
        OrderList orderList = new OrderList(customer1.getId(),
                                            List.of(
                                                    new ProductInformation(product1.getId(), 3),
                                                    new ProductInformation(product2.getId(), 1)
                                            ));

        var requestBuilder = MockMvcRequestBuilders.post("/order/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(orderList));

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.jsonPath("$.customer.id").value(customer1.getId()),
                        MockMvcResultMatchers.jsonPath("$.orderList.length()").value(2)
                );

    }

    @Test
    @DisplayName("POST /order возвращает HTTP-ответ со статусом BAD REQUEST")
    void createOrder_ReturnErrorMessage() throws Exception
    {
        // given
        OrderList orderList = new OrderList(customer1.getId(), new ArrayList<>());

        var requestBuilder = MockMvcRequestBuilders.post("/order/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(orderList));

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isBadRequest()
                );

    }

    @Test
    @DisplayName("DELETE /order/delete возвращает HTTP-ответ со статусом 200 OK")
    void deleteOrder_ReturnValidResponseEntity() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        this.orderRepository.save(order);

        var requestBuilder = MockMvcRequestBuilders.delete("/order/delete")
                .param("id", order.getId().toString());

        // when
        this.mockMvc.perform(requestBuilder).
                //then
                        andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(0, this.orderRepository.findAll().size());
    }

    @Test
    @DisplayName("DELETE order/product/delete возвращает HTTP-ответ со статусом 200 OK с ответом \"The order has been updated\"")
    void deleteProductFromOrder_ReturnMessageAboutUpdate() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        this.orderRepository.save(order);

        var requestBuilder = MockMvcRequestBuilders.delete("/order/product/delete")
                .param("orderId", order.getId().toString())
                .param("productId", product1.getId().toString());

        // when
        this.mockMvc.perform(requestBuilder)
                //then
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$").value("The order has been updated"));

        List<Order> order1 = this.orderRepository.getByIdNotProxy(order.getId());

        assertEquals( 1, order1.get(0).getOrderList().size());
        assertEquals(product2.getId(), order1.get(0).getOrderList().stream().findFirst().get().getProduct().getId());

    }

    @Test
    @DisplayName("DELETE order/product/delete возвращает HTTP-ответ со статусом 200 OK с ответом \"The order has been removed\")")
    void deleteProductFromOrder_ReturnMessageAboutRemove() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);

        this.orderRepository.save(order);

        var requestBuilder = MockMvcRequestBuilders.delete("/order/product/delete")
                .param("orderId", order.getId().toString())
                .param("productId", product1.getId().toString());

        // when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("The order has been removed"));

        assertEquals( 0, this.orderRepository.findAll().size());

    }

    @Test
    @DisplayName("PUT /order/status/update возвращает HTTP-ответ со статусом 200 OK и меняет статус заказа")
    void changeOrderStatus_ReturnValidResponseEntity() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        this.orderRepository.save(order);

        var requestBuilder = MockMvcRequestBuilders.put("/order/status/update")
                .param("id", order.getId().toString());

        // when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Order> order1 = this.orderRepository.getByIdNotProxy(order.getId());

        assertEquals(true, order1.get(0).getStatus());
    }

    @Test
    @DisplayName("PUT /order/product/update возвращает HTTP-ответ со статусом 200 OK и меняет количество продуктов одного типа в заказе")
    void changeOrderStatus_updateOrder() throws Exception
    {
        // given
        Order order = new Order(customer1);
        order.addProduct(product1, 3);
        order.addProduct(product2, 1);

        this.orderRepository.save(order);

        ChangeQuantity changeQuantity = new ChangeQuantity(order.getId(), product2.getId(), 2);

        var requestBuilder = MockMvcRequestBuilders.put("/order/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(changeQuantity));

        // when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Order> order1 = this.orderRepository.getByIdNotProxy(order.getId());

        OrdersProducts res =  order1.get(0).getOrderList().stream().filter(x -> x.getProduct().getId().equals(product2.getId())).findFirst().orElse(null);

        assertEquals( 2, res.getQuantity());
    }
}