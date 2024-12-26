package com.example.petProject.controllers;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import com.example.petProject.dto.ChangeQuantity;
import com.example.petProject.dto.OrderList;
import com.example.petProject.dto.ProductInformation;
import com.example.petProject.services.CustomerServiceInterface;
import com.example.petProject.services.OrderServiceInterface;
import com.example.petProject.services.OrdersProductsServiceInterface;
import com.example.petProject.services.ProductServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTestUnit {

    @Mock
    OrderServiceInterface orderService;
    @Mock
    OrdersProductsServiceInterface ordersProductsService;
    @Mock
    CustomerServiceInterface customerService;
    @Mock
    ProductServiceInterface productService;

    @InjectMocks
    OrdersController controller;


    // todo
    // прописать equals для класса?
    @Test
    @DisplayName("GET /orders возвращает HTTP-ответ со статусом 200 OK и список всех незавершенных заказов")
    void allOrders_ReturnValidResponseEntity()
    {
        // given
        Customer customer1 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        Customer customer2 = new Customer(2L, "Леонид", "Каневский",
                "77777777777", "Загадочная", "15а", 1);
        List<OrderDTO> orders = List.of((new Order(customer1)).orderToOrderDTO(), (new Order(customer2)).orderToOrderDTO());

        Mockito.doReturn(orders).when(orderService).findAllOrders(0, 10);

        // when
        var response = this.controller.allOrders(1,10);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());

    }

    @Test
    @DisplayName("GET /order с UUID id в качестве параметра возвращает HTTP-ответ со статусом 200 OK и заказ с указанным id")
    void findOrderById_ReturnValidResponseEntity()
    {
        // given
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        OrderDTO order = (new Order(customer)).orderToOrderDTO();
        UUID id = order.getId();

        Mockito.doReturn(order).when(orderService).getOrderDTOById(id);

        // when
        var response = this.controller.findOrderById(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    @DisplayName("GET /order/{id} возвращает HTTP-ответ со статусом 200 OK и заказы с указанным id пользователя")
    void findOrderByUserId_ReturnValidResponseEntity()
    {

        // given
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        Long id = 1L;
        List<OrderDTO> orders = List.of((new Order(customer)).orderToOrderDTO(), (new Order(customer)).orderToOrderDTO());

        Mockito.doReturn(orders).when(orderService).findOrdersByUserId(id, 0, 10);

        // when
        var response = this.controller.findOrderByUserId(id, 1, 10);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());

    }

    @Test
    @DisplayName("POST /order возвращает HTTP-ответ со статусом 200 OK")
    void createOrder_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;
        List<ProductInformation> products = List.of(new ProductInformation(1L, 2),
                new ProductInformation(2L, 2));

        OrderList orderList = new OrderList(id, products);
        Customer customer = new Customer();
        OrderDTO orderDTO = new OrderDTO();

        Mockito.doReturn(customer).when(customerService).getCustomerById(id);

        Mockito.doReturn(new Product(1L, "молоко", 76)).when(productService).getProductById(1L);
        Mockito.doReturn(new Product(2L, "хлеб", 64)).when(productService).getProductById(2L);

        Mockito.doReturn(orderDTO).when(orderService).saveOrder(Mockito.any(Order.class));

        // when
        var response = this.controller.createOrder(orderList);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());

    }

    @Test
    @DisplayName("POST /order возвращает HTTP-ответ со статусом 400 BAD REQUEST")
    void createOrder_ReturnInvalidResponseEntity()
    {
        // given
        Long id = 1L;
        List<ProductInformation> products = new ArrayList<ProductInformation>();

        OrderList orderList = new OrderList(id, products);

        // then
        assertThrows(ResponseStatusException.class, () -> this.controller.createOrder(orderList));
        Mockito.verifyNoInteractions(this.orderService);
        Mockito.verifyNoInteractions(this.productService);
        Mockito.verifyNoInteractions(this.customerService);

    }

    @Test
    @DisplayName("DELETE /order возвращает HTTP-ответ со статусом 200 OK")
    void deleteOrder_ReturnValidResponseEntity()
    {
        // given
        UUID id = UUID.randomUUID();

        // when
        var response = this.controller.deleteOrder(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The order has been removed", response.getBody());
        Mockito.verify(this.orderService).deleteOrder(id);
    }

    @Test
    @DisplayName("DELETE /order/product возвращает HTTP-ответ со статусом 200 OK и удаляет один продукт")
    void deleteProductFromOrder_ReturnValidResponseEntityDeleteProductOnly()
    {
        // given
        UUID orderId = UUID.randomUUID();
        Long productId = 1L;

        OrdersProducts partOfOrder1 = new OrdersProducts();
        OrdersProductsId ordersProductsId = new OrdersProductsId(productId, orderId);
        partOfOrder1.setId(ordersProductsId);

        OrdersProducts partOfOrder2 = new OrdersProducts();
        partOfOrder2.setId(new OrdersProductsId(2L, orderId));

        Set<OrdersProducts> products = new HashSet<>();
        products.add(partOfOrder2);
        products.add(partOfOrder1);

        Order order = new Order(new Customer(), products);

        // when

        Mockito.doReturn(partOfOrder1).when(ordersProductsService).getOrdersProductsById(ordersProductsId);
        Mockito.doReturn(order).when(orderService).getOrderById(orderId);

        var response = this.controller.deleteProductFromOrder(orderId,productId);

        // then

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The order has been updated", response.getBody());
        Mockito.verify(this.orderService).saveOrder(order);

    }

    @Test
    @DisplayName("DELETE /order/product возвращает HTTP-ответ со статусом 200 OK и удаляет весь заказ")
    void deleteProductFromOrder_ReturnValidResponseEntityDeleteAllOrder()
    {
        // given
        UUID orderId = UUID.randomUUID();
        Long productId = 1L;

        OrdersProducts partOfOrder = new OrdersProducts();
        OrdersProductsId ordersProductsId = new OrdersProductsId(productId, orderId);
        partOfOrder.setId(ordersProductsId);

        Set<OrdersProducts> products = new HashSet<>();
        products.add(partOfOrder);

        Order order = new Order(new Customer(), products);

        // when

        Mockito.doReturn(partOfOrder).when(ordersProductsService).getOrdersProductsById(ordersProductsId);
        Mockito.doReturn(order).when(orderService).getOrderById(orderId);

        var response = this.controller.deleteProductFromOrder(orderId,productId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The order has been removed", response.getBody());
        Mockito.verify(this.orderService).deleteOrder(orderId);

    }

    @Test
    @DisplayName("PUT order/status возвращает HTTP-ответ со статусом 200 OK")
    void changeOrderStatus_ReturnValidResponseEntity()
    {
        // given
        UUID id = UUID.randomUUID();

        // when
        var response = this.controller.changeOrderStatus(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The order has been updated", response.getBody());
        Mockito.verify(this.orderService).changeOrdersStatus(id);
    }

    @Test
    @DisplayName("PUT order/product возвращает HTTP-ответ со статусом 200 OK")
    void updateOrder_ReturnValidResponseEntity()
    {
        // given
        UUID orderId = UUID.randomUUID();
        OrdersProductsId id = new OrdersProductsId(1L, orderId);

        // when
        var response = this.controller.updateOrder(new ChangeQuantity(orderId, 1L, 2));

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The order has been updated", response.getBody());
        Mockito.verify(this.ordersProductsService).changeProductQuantity(id, 2);
    }

}