package com.example.petProject.services.impl;

import com.example.petProject.models.Customer;
import com.example.petProject.repositories.CustomerRepository;
import com.example.petProject.services.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerServiceInterface {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<Customer> listOfCustomers = customers.getContent();

        return listOfCustomers;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
