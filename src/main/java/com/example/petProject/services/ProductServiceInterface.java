package com.example.petProject.services;

import com.example.petProject.models.Product;

import java.util.List;

public interface ProductServiceInterface {

    List<Product> findAllProducts();

    void saveProduct(Product product);

    void deleteProduct(Long id);

    Product getProductById(Long id);

}
