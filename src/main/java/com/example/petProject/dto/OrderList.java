package com.example.petProject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    private Long userId;
    @NotNull
    @Valid
    private List<ProductInformation> products;


}
