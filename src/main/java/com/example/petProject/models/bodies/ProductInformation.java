package com.example.petProject.models.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
//todo
// может, стоит сделать какой-то конструктор, который посчитает totalCost
@Data
public class ProductInformation extends Product {
    private double totalCost;

}
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformation {
    private Long productId;
    private int quantity;

}