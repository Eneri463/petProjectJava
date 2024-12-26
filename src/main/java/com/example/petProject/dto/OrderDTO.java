package com.example.petProject.dto;

import com.example.petProject.models.Customer;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    public UUID id;
    public String date;

    public Set<ProductInListDTO> orderList;
    public Customer customer;
}
