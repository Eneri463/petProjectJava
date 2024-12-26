package com.example.petProject.dto;


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
    @Min(1)
    private Long productId;
    @NotNull
    @Min(1)
    private int quantity;

}