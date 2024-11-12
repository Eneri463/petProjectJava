package com.example.petProject.models.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

//todo
// добавить ограничения тпио NotNull
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderList {

    Long userId;
    List<ProductInformation> products;
}
