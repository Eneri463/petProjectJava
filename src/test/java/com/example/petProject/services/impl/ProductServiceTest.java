package com.example.petProject.services.impl;

import com.example.petProject.models.Product;
import com.example.petProject.repositories.ProductRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService service;

    @Test
    @DisplayName("findAllProducts возвращает список всех продуктов из БД")
    void findAllProducts_ReturnValidResponseEntity()
    {
        // given
        List<Product> products = List.of(new Product(1L, "Молоко", 75.5),
                new Product(2L, "Хлеб", 53.0));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> pages = new PageImpl<>(products, pageable, 0);

        Mockito.doReturn(pages).when(productRepository).findAll(Mockito.any(Pageable.class));

        // when
        var response = this.service.findAllProducts(0,10);

        // then
        assertNotNull(response);
        assertEquals(products,response);
    }

    @Test
    @DisplayName("saveProduct сохраняет продукт в БД")
    void saveProduct_ReturnValidResponseEntity()
    {
        Product product = new Product(1L, "Молоко", 75.5);
        Mockito.when(this.productRepository.save(product)).thenReturn(product);

        var response = service.saveProduct(product);

        assertNotNull(response);
        assertEquals(product,response);
    }

    @Test
    @DisplayName("deleteProduct удаляет продукт с указанным id из БД")
    void deleteProduct_ReturnValidResponseEntity()
    {
        service.deleteProduct(1L);

        Mockito.verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("getProductById возвращает продукт с указанным id из БД")
    void getProductById_ReturnValidResponseEntity()
    {
        Product product = new Product(1L, "Молоко", 75.5);
        Mockito.when(this.productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        var response = this.service.getProductById(1L);

        // then
        assertNotNull(response);
        assertEquals(product,response);
    }



}