package com.example.petProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;


@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}
