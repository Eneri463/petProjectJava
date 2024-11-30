package com.example.petProject.models.requestBodies;

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
    private Long productId;

    @NotNull
    @Min(1)
    private int quantity;
}
