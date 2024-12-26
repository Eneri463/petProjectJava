package com.example.petProject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeQuantity {
    @NotNull
    private UUID orderId;
    @NotNull
    @Min(1)
    private Long productId;

    @NotNull
    @Min(1)
    private int quantity;
}
