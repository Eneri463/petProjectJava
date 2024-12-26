package com.example.petProject.controllers;

import com.example.petProject.dto.OrderDTO;
import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;
import com.example.petProject.models.OrdersProducts;
import com.example.petProject.models.Product;
import com.example.petProject.dto.ChangeQuantity;
import com.example.petProject.dto.OrderList;
import com.example.petProject.dto.ProductInformation;
import com.example.petProject.models.compositeKeys.OrdersProductsId;
import com.example.petProject.services.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    @Valid
    public ResponseEntity<List<OrderDTO>> allOrders(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) @Min(1) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Min(1) int pageSize
    )
    {
        return ResponseEntity.ok(orderService.findAllOrders(pageNo-1, pageSize));
    }

    // получение заказа с указанным id
    @GetMapping("/order")
    public ResponseEntity<OrderDTO> findOrderById(@RequestParam UUID id)
    {
        return new ResponseEntity<>(orderService.getOrderDTOById(id), HttpStatus.OK);
    }

    // получение всех невыполненных заказов пользователя с указанным userId
    @GetMapping("/order/customer/{id}")
    @Valid
    public ResponseEntity<List<OrderDTO>> findOrderByUserId(
            @PathVariable Long id,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) @Min(1) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Min(1) int pageSize
    )
    {
        return ResponseEntity.ok(orderService.findOrdersByUserId(id, pageNo-1, pageSize));
    }


    // создание заказа
    @PostMapping("/order/create")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderList request)
    {
        if (request.getProducts().size() == 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The order should not be empty");
        }

        Customer customer = customerService.getCustomerById(request.getUserId());

        Order order = new Order(customer);

        for (ProductInformation item : request.getProducts())
        {
            Product newProduct = productService.getProductById(item.getProductId());
            order.addProduct(newProduct, item.getQuantity());
        }

        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    // удаление заказа по его id
    @DeleteMapping("/order/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam UUID id)
    {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("The order has been removed");
    }

    // удаление продукта из заказа
    @DeleteMapping("/order/product/delete")
    public ResponseEntity<String> deleteProductFromOrder(@RequestParam UUID orderId, @RequestParam Long productId)
    {
        OrdersProducts partOfOrder = ordersProductsService.getOrdersProductsById(new OrdersProductsId(productId, orderId));

        Order order = orderService.getOrderById(orderId);

        order.removeProduct(partOfOrder);

        if (order.getOrderList().size() == 0)
        {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("The order has been removed");
        }

        orderService.saveOrder(order);

        return ResponseEntity.ok("The order has been updated");
    }

    // отметить заказ как завершенный
    @PutMapping("/order/status/update")
    public ResponseEntity<String> changeOrderStatus(@RequestParam UUID id)
    {
        orderService.changeOrdersStatus(id);
        return ResponseEntity.ok("The order has been updated");
    }

    // редактирование количества продуктов в заказе
    @PutMapping("/order/product/update")
    public ResponseEntity<String> updateOrder(@Valid @RequestBody ChangeQuantity requestBody)
    {

        OrdersProductsId id = new OrdersProductsId(requestBody.getProductId(), requestBody.getOrderId());

        ordersProductsService.changeProductQuantity(id, requestBody.getQuantity());

        return ResponseEntity.ok("The order has been updated");
    }
}
