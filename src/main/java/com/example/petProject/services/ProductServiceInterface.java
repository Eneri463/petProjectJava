package com.example.petProject.services;

import com.example.petProject.models.Product;

import java.util.List;

public interface ProductServiceInterface {

    List<Product> findAllProducts(int pageNo, int pageSize);

    Product saveProduct(Product product);

    void deleteProduct(Long id);

    Product getProductById(Long id);

}
