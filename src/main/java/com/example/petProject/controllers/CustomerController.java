package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.services.CustomerServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@AllArgsConstructor
public class CustomerController {

    private final CustomerServiceInterface customerService;

    // ------------------------------------------------------------

    @GetMapping("/customers")
    public List<Customer> allCustomers()
    {
        return customerService.findAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public Customer findCustomerById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/customer")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long id)
    {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("The customer has been removed");
    }

    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer)
    {
        customerService.saveCustomer(customer);
        return ResponseEntity.ok("The customer has been created");
    }

    // ------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        return new ResponseEntity<>("Incorrect data entered", HttpStatus.BAD_REQUEST);
    }

}
