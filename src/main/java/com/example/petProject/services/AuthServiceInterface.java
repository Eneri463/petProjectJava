package com.example.petProject.services;

import com.example.petProject.dto.AuthResponseDTO;
import org.springframework.security.core.Authentication;

public interface AuthServiceInterface {

    AuthResponseDTO authenticate(String username, String password);

    AuthResponseDTO refresh();

    String getAccessToken(Authentication authentication);

    String getRefreshToken(Authentication authentication);

}
