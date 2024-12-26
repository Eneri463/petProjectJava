package com.example.petProject.controllers;

import com.example.petProject.models.Product;
import com.example.petProject.services.ProductServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTestUnit {

    @Mock
    ProductServiceInterface productService;

    @InjectMocks
    ProductController controller;

    @Test
    @DisplayName("GET /products возвращает HTTP-ответ со статусом 200 OK и список всех продуктов")
    void allProducts_ReturnValidResponseEntity()
    {
        // given
        List<Product> products = List.of(new Product(1L, "Молоко", 75.5),
                                          new Product(2L, "Хлеб", 53.0));

        Mockito.doReturn(products).when(this.productService).findAllProducts(0,10);

        // when
        var response = this.controller.allProducts(1,10);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    @DisplayName("GET /products/{id} возвращает HTTP-ответ со статусом 200 OK и продукт с указанным id")
    void findProductById_ReturnValidResponseEntity()
    {
        Long id = 1L;
        // given
        Product product = new Product(1L, "Молоко", 75.5);

        Mockito.doReturn(product).when(this.productService).getProductById(id);

        // when
        var response = this.controller.findProductById(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    @DisplayName("DELETE /product возвращает HTTP-ответ со статусом 200 OK")
    void deleteProduct_ReturnValidResponseEntity()
    {
        // given
        Long id = 1L;

        // when
        var response = this.controller.deleteProduct(id);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The product has been removed", response.getBody());
        Mockito.verify(this.productService).deleteProduct(id);
    }

    @Test
    @DisplayName("POST /product возвращает HTTP-ответ со статусом 200 OK")
    void createProduct_ReturnValidResponseEntity()
    {
        // given
        Product product = new Product(1L, "Молоко", 75.5);

        Mockito.when(this.productService.saveProduct(product)).thenReturn(product);

        // when
        var response = this.controller.createProduct(product);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }




}