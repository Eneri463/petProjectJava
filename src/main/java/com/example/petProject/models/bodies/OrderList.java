package com.example.petProject.models.bodies;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderList {

    @NotNull
    private Long userId;
    @NotNull
    private List<ProductInformation> products;
}
