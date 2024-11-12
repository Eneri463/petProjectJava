package com.example.petProject.services;

import com.example.petProject.models.Customer;
import com.example.petProject.models.Order;

import java.util.List;

public interface CustomerServiceInterface {

    List<Customer> findAllCustomers();

    void saveCustomer(Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomerById(Long id);

}
