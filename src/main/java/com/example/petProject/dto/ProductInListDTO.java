package com.example.petProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInListDTO {
    Long id;
    String name;
    int quantity;
}
