package com.example.petProject.dto;

import com.example.petProject.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

    Long id;

    String username;

    String password;
}
