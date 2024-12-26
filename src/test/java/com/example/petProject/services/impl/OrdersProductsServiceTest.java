package com.example.petProject.services.impl;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import com.example.petProject.repositories.OrdersProductsRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdersProductsServiceTest {

    @Mock
    OrdersProductsRepository ordersProductsRepository;

    @InjectMocks
    OrdersProductsService service;

    @Test
    @DisplayName("findAllProducts возвращает список всех заказанных продуктов из заказов")
    void findAllProducts_ReturnValidResponseEntity()
    {
        // given
        List<OrdersProducts> orders = List.of
        (
            new OrdersProducts
                    (
                    new Order(new Customer(1L, "Валерий", "Меладзе",
                    "88888888888", "Популярная", "30", 1)),
                    new Product(1L, "молоко", 65),
                    3
                    ),
            new OrdersProducts
                    (
                    new Order(new Customer(2L, "Альбина", "Джанабаева",
                    "77777777777", "Популярная", "30", 1)),
                    new Product(2L, "масло", 80),
                    2
                    )
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<OrdersProducts> pages = new PageImpl<>(orders, pageable, 0);

        Mockito.doReturn(pages).when(ordersProductsRepository).findAll(Mockito.any(Pageable.class));

        // when
        var response = this.service.findAllProductsInOrder(0,10);

        // then
        assertNotNull(response);
        assertEquals(orders,response);
    }

    @Test
    @DisplayName("saveOrdersProducts сохраняет указанный заказ на продукт")
    void saveOrdersProducts_ReturnValidResponseEntity()
    {
        OrdersProducts order = new OrdersProducts
            (
                    new Order(new Customer(1L, "Валерий", "Меладзе",
                            "88888888888", "Популярная", "30", 1)),
                    new Product(1L, "молоко", 65),
                    3
            );

        Mockito.when(this.ordersProductsRepository.save(order)).thenReturn(order);

        var response = service.saveOrdersProducts(order);

        assertNotNull(response);
        assertEquals(order,response);
    }

    @Test
    @DisplayName("deleteOrdersProducts удаляет продукт заказа")
    void deleteOrdersProducts_ReturnValidResponseEntity()
    {
        OrdersProductsId id = new OrdersProductsId(1L, UUID.randomUUID());

        service.deleteOrdersProducts(id);

        Mockito.verify(this.ordersProductsRepository).deleteById(id);
    }

    @Test
    @DisplayName("getOrdersProductsById берет из БД заказ на продукт с указанным id")
    void getOrdersProductsById_ReturnValidResponseEntity()
    {
        // given
        OrdersProducts order = new OrdersProducts
                (
                        new Order(new Customer(1L, "Валерий", "Меладзе",
                                "88888888888", "Популярная", "30", 1)),
                        new Product(1L, "молоко", 65),
                        3
                );
        OrdersProductsId id = order.getId();
        Mockito.when(this.ordersProductsRepository.findById(id)).thenReturn(Optional.of(order));

        // when
        var response = this.service.getOrdersProductsById(id);

        // then
        assertNotNull(response);
        assertEquals(order,response);
    }

    @Test
    @DisplayName("getAllProductsInOrderByOrdersId возвращает список всех продуктов в заказе с указанным id")
    void getAllProductsInOrderByOrdersId_ReturnValidResponseEntity()
    {
        // given
        Order order = new Order(new Customer(1L, "Валерий", "Меладзе",
                "88888888888", "Популярная", "30", 1));
        UUID id = order.getId();


        List<OrdersProducts> orders = List.of(new OrdersProducts(order, new Product(1L, "молоко", 65), 3),
                new OrdersProducts(order, new Product(2L, "масло", 80), 2));

        Mockito.doReturn(orders).when(ordersProductsRepository).findAllByOrderId(id);

        // when
        var response = this.service.getAllProductsInOrderByOrdersId(id);

        // then
        assertNotNull(response);
        assertEquals(orders,response);
    }

    @Test
    @DisplayName("changeProductQuantity изменяет количество продуктов в заказе")
    void changeProductQuantity_ReturnValidResponseEntity()
    {
        OrdersProductsId id = new OrdersProductsId(1L, UUID.randomUUID());

        service.changeProductQuantity(id, 3);

        Mockito.verify(this.ordersProductsRepository).setValue(id,3);
    }
}