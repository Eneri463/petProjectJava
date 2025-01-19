package com.example.petProject.services.impl;

import com.example.petProject.models.RefreshToken;
import com.example.petProject.repositories.RefreshTokenRepository;
import com.example.petProject.services.RefreshTokenServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenServiceInterface {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public void delete(Long id)
    {
        refreshTokenRepository.deleteById(id);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken)
    {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean existsInDb(RefreshToken refreshToken)
    {
        RefreshToken refreshTokenNew = refreshTokenRepository.findById(refreshToken.getId()).orElse(null);

        if (refreshTokenNew == null)
            return false;

        if (refreshTokenNew.getToken().equals(refreshToken.getToken()))
            return true;

        return false;
    }

}
