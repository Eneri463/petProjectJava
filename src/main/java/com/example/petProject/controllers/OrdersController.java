package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.models.bodies.ChangeQuantityRequestBody;
import com.example.petProject.models.bodies.OrderList;
import com.example.petProject.models.bodies.ProductInformation;
import com.example.petProject.services.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

    //todo
    // проверить, чтобы во всех классах создавался один и тот же экземпляр
    private final CustomerServiceInterface customerService;
    private final ProductServiceInterface productService;

    // ------------------------------------------------------------

    // получение всех невыполненных заказов
    @GetMapping("/orders")
    public List<Order> allOrders()
    {
        return orderService.findAllOrders();
    }

    // получение заказа с указанным id
    @GetMapping("/order")
    public Order findOrderById(@RequestParam UUID id)
    {
        return orderService.getOrderById(id);
    }

    // получение всех невыполненных заказов пользователя с указанным userId
    @GetMapping("/order/user/{id}")
    public List<Order> findOrderByUserId(@PathVariable Long id)
    {
       return orderService.findOrdersByUserId(id);
    }

    // создание заказа
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderList request)
    {
        Customer customer = customerService.getCustomerById(request.getUserId());

        Order order = new Order(customer);

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

    @DeleteMapping("order/product")
    // удаление продукта из заказа
    public ResponseEntity<String> deleteProductFromOrder(@RequestParam UUID orderId, @RequestParam Long productId)
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

    // отметить заказ как завершенный
    @PutMapping("order/status")
    public ResponseEntity<String> changeOrderStatus(@RequestParam UUID id)
    {
        orderService.changeOrdersStatus(id);
        return ResponseEntity.ok("The order has been updated");
    }


    @PutMapping("order/product")
    // редактирование количества продуктов в заказе
    public ResponseEntity<String> updateOrder(@RequestBody ChangeQuantityRequestBody requestBody)
    {

        Order order = orderService.getOrderById(requestBody.getOrderId());

        for(OrdersProducts ordersPart: order.getOrderList())
        {
            if (ordersPart.getProduct().getId() == requestBody.getProductId())
            {
                ordersProductsService.changeProductQuantity(ordersPart.getId(), requestBody.getQuantity());
                break;
            }
        }

        return ResponseEntity.ok("The order has been updated");
    }
}
