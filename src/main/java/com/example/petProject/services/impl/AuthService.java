package com.example.petProject.services.impl;

import com.example.petProject.dto.AuthResponseDTO;
import com.example.petProject.repositories.RefreshTokenRepository;
import com.example.petProject.security.tokensFactory.TokenGenerator;
import com.example.petProject.services.AuthServiceInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenGenerator tokenGenerator;

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @Override
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

    @Override
    public AuthResponseDTO refresh()
    {
        var context = SecurityContextHolder.getContext();

        String accessToken = tokenGenerator.generateAccessToken(context.getAuthentication());

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                accessToken,
                null);

        return authResponseDTO;
    }

    @Override
    public String getAccessToken(Authentication authentication)
    {
        return tokenGenerator.generateAccessToken(authentication);
    }

    @Override
    public String getRefreshToken(Authentication authentication)
    {
        return tokenGenerator.generateRefreshToken(authentication);
    }

}
