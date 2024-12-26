package com.example.petProject.services.impl;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.repositories.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService service;

    @Test
    @DisplayName("findAllOrders возвращает из БД список всех заказов")
    void findAllOrders_ReturnValidResponseEntity()
    {
        // given
        Customer customer1 = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);
        Customer customer2 = new Customer(2L, "Леонид", "Каневский",
                "77777777777", "Загадочная", "15а", 1);

        List<Order> orders = List.of(new Order(customer1), new Order(customer2));
        List<OrderDTO> ordersDTO = List.of(orders.get(0).orderToOrderDTO(), orders.get(1).orderToOrderDTO());

        Mockito.doReturn(orders).when(orderRepository).findAllOrderIn(0, 10);

        // when
        var response = this.service.findAllOrders(0, 10);

        // then
        assertNotNull(response);
        assertEquals(ordersDTO, response);

    }

    @Test
    @DisplayName("findOrdersByUserId возвращает из БД заказы с указанным id пользователя")
    void findOrdersByUserId_ReturnValidResponseEntity()
    {
        // given
        Customer customer = new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1);

        Long id = 1L;

        List<Order> orders = List.of(new Order(customer), new Order(customer));
        List<OrderDTO> ordersDTO = List.of(orders.get(0).orderToOrderDTO(), orders.get(1).orderToOrderDTO());

        Mockito.doReturn(orders).when(orderRepository).findByUserId(id,0,10);

        // when
        var response = this.service.findOrdersByUserId(id,0,10);

        // then
        assertNotNull(response);
        assertEquals(ordersDTO,response);
    }

    @Test
    @DisplayName("deleteOrder инициирует удаление из БД заказа с указанным id")
    void deleteOrder_ReturnValidResponseEntity()
    {
        UUID id = UUID.randomUUID();

        this.service.deleteOrder(id);

        Mockito.verify(this.orderRepository).deleteById(id);

    }

    @Test
    @DisplayName("saveOrder инициирует сохранение в БД указанного заказа")
    void saveOrder_ReturnValidResponseEntity()
    {
        // given

        Order order = new Order(new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1));

        Mockito.when(this.orderRepository.save(order)).thenReturn(order);

        // when
        var response = this.service.saveOrder(order);

        // then
        assertNotNull(response);
        assertEquals(order.orderToOrderDTO(), response);
    }

    @Test
    @DisplayName("getOrderDTOById возвращает из БД заказ с указанным id")
    void getOrderDTOById_ReturnValidResponseEntity()
    {
        // given
        Order order = new Order(new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1));
        UUID id = order.getId();

        Mockito.when(this.orderRepository.getByIdNotProxy(id)).thenReturn(List.of(order));

        // when
        var response = this.service.getOrderDTOById(id);

        // then
        assertNotNull(response);
        assertEquals(order.orderToOrderDTO(), response);
    }

    @Test
    @DisplayName("getOrderById возвращает из БД заказ с указанным id")
    void getOrderById_ReturnValidResponseEntity()
    {
        // given
        Order order = new Order(new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1));
        UUID id = order.getId();

        Mockito.when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));


        // when
        var response = this.service.getOrderById(id);

        // then
        assertNotNull(response);
        assertEquals(order,response);
    }

    @Test
    @DisplayName("changeOrdersStatus меняет статус указанного заказа")
    void changeOrdersStatus_ReturnValidResponseEntity()
    {
        // given
        UUID id = UUID.randomUUID();

        // when
        this.service.changeOrdersStatus(id);

        // then
        Mockito.verify(this.orderRepository).setValue(id);
    }

}