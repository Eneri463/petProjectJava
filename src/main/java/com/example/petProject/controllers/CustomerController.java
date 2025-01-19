package com.example.petProject.controllers;

import com.example.petProject.models.Customer;
import com.example.petProject.services.CustomerServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@AllArgsConstructor
public class CustomerController {

    private final CustomerServiceInterface customerService;

    // ------------------------------------------------------------

    @PreAuthorize("hasAuthority('JWT_ACCESS')")
    @GetMapping("/customers")
    @Valid
    public ResponseEntity<List<Customer>> allCustomers(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) @Min(1) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) @Min(1) int pageSize
    )
    {
        return ResponseEntity.ok(customerService.getAllCustomers(pageNo-1, pageSize));
    }

    @PreAuthorize("hasAuthority('JWT_ACCESS')")
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id)
    {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PreAuthorize("hasAuthority('JWT_ACCESS')")
    @DeleteMapping("/customer/delete")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long id)
    {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("The customer has been removed");
    }

    @PreAuthorize("hasAuthority('JWT_ACCESS')")
    @PostMapping("/customer/create")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer)
    {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }
}
