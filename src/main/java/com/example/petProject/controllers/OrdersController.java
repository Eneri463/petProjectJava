package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.models.requestBodies.ChangeQuantity;
import com.example.petProject.models.requestBodies.OrderList;
import com.example.petProject.models.requestBodies.ProductInformation;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import com.example.petProject.models.projections.OrderProjection;
import com.example.petProject.services.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@EnableAutoConfiguration
@AllArgsConstructor
public class OrdersController {
    private final OrderServiceInterface orderService;
    private final OrdersProductsServiceInterface ordersProductsService;
    private final CustomerServiceInterface customerService;
    private final ProductServiceInterface productService;

    // ------------------------------------------------------------

    // получение всех невыполненных заказов
    @GetMapping("/orders")
    public List<OrderProjection> allOrders()
    {
        return orderService.findAllOrders();
    }

    // получение заказа с указанным id
    @GetMapping("/order")
    public OrderProjection findOrderById(@RequestParam UUID id)
    {
        OrderProjection res = orderService.getOrderProjectionById(id);
        return res;
    }

    // получение всех невыполненных заказов пользователя с указанным userId
    @GetMapping("/order/user/{id}")
    public List<OrderProjection> findOrderByUserId(@PathVariable Long id)
    {
       return orderService.findOrdersByUserId(id);
    }

    // создание заказа
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderList request)
    {
        if (request.getProducts().size() == 0)
        {
            return new ResponseEntity<>("The order should not be empty", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerService.getCustomerById(request.getUserId());

        Order order = new Order(customer);

        //todo
        // что будет, если добавить заказ с одним товаром дважды
        for (ProductInformation item : request.getProducts())
        {
            Product newProduct = productService.getProductById(item.getProductId());
            order.addProduct(newProduct, item.getQuantity());
        }

        orderService.saveOrder(order);

        return ResponseEntity.ok("The order has been created");
    }

    // удаление заказа по его id
    @DeleteMapping("/order")
    public ResponseEntity<String> deleteOrder(@RequestParam UUID id)
    {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("The order has been removed");
    }

    // удаление продукта из заказа
    @DeleteMapping("order/product1")
    public ResponseEntity<String> deleteProductFromOrder1(@RequestParam UUID orderId, @RequestParam Long productId)
    {
        Order order = orderService.getOrderById(orderId);

        Set<OrdersProducts> list = order.getOrderList();

        for(OrdersProducts ordersPart: list)
        {
            if (ordersPart.getProduct().getId() == productId)
            {
                ordersPart.setOrder(null);
                order.removeProduct(ordersPart);
            }
        }

        if (order.getOrderList().size() == 0)
        {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("The order has been removed");
        }

        return ResponseEntity.ok("The order has been updated");
    }

    // удаление продукта из заказа
    @DeleteMapping("order/product2")
    public ResponseEntity<String> deleteProductFromOrder2(@RequestParam UUID orderId, @RequestParam Long productId)
    {
        OrdersProducts partOfOrder = ordersProductsService.getOrdersProductsById(new OrdersProductsId(productId, orderId));

        Order order = orderService.getOrderById(orderId);

        order.removeProduct(partOfOrder);

        if (order.getOrderList().size() == 0)
        {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("The order has been removed");
        }

        return ResponseEntity.ok("The order has been updated");
    }

    // отметить заказ как завершенный
    @PutMapping("order/status")
    public ResponseEntity<String> changeOrderStatus(@RequestParam UUID id)
    {
        orderService.changeOrdersStatus(id);
        return ResponseEntity.ok("The order has been updated");
    }

    // редактирование количества продуктов в заказе
    @PutMapping("order/product")
    public ResponseEntity<String> updateOrder(@Valid @RequestBody ChangeQuantity requestBody)
    {

        OrdersProductsId id = new OrdersProductsId(requestBody.getProductId(), requestBody.getOrderId());

        ordersProductsService.changeProductQuantity(id, requestBody.getQuantity());

        return ResponseEntity.ok("The order has been updated");
    }
}
