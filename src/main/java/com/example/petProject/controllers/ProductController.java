package com.example.petProject.controllers;

import com.example.petProject.models.Product;
import com.example.petProject.services.ProductServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@EnableAutoConfiguration
@Validated
public class ProductController {

    private final ProductServiceInterface productService;

    // ------------------------------------------------------------

    @GetMapping("/products")
    @Valid
    public ResponseEntity<List<Product>> allProducts(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) @Min(1) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Min(1) int pageSize
    )
    {
        return ResponseEntity.ok(productService.findAllProducts(pageNo-1, pageSize));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id)
    {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/product/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.ok("The product has been removed");
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product)
    {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }
}
