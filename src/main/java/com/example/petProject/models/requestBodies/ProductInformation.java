package com.example.petProject.models.requestBodies;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformation {
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private int quantity;

}