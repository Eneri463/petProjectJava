package com.example.petProject.controllers;

import com.example.petProject.models.Product;
import com.example.petProject.services.ProductServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@EnableAutoConfiguration
public class ProductController {

    private final ProductServiceInterface productService;

    // ------------------------------------------------------------

    @GetMapping("/products")
    public List<Product> allProducts()
    {
        return productService.findAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @DeleteMapping("/product")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.ok("The product has been removed");
    }

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Product product)
    {
        productService.saveProduct(product);
        return ResponseEntity.ok("The product has been created");
    }
}
