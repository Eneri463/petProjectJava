package com.example.petProject.services.impl;

import com.example.petProject.dto.AuthResponseDTO;
import com.example.petProject.security.tokensFactory.TokenGenerator;
import com.example.petProject.services.AuthServiceInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenGenerator tokenGenerator;

    public AuthResponseDTO authenticate(String username, String password)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                getAccessToken(authentication),
                getRefreshToken(authentication));

        return authResponseDTO;
    }

    public String getAccessToken(Authentication authentication)
    {
        return tokenGenerator.generateAccessToken(authentication);
    }

    public String getRefreshToken(Authentication authentication)
    {
        return tokenGenerator.generateRefreshToken(authentication);
    }

}
