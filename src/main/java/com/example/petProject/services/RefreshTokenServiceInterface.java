package com.example.petProject.services;

import com.example.petProject.models.RefreshToken;

public interface RefreshTokenServiceInterface {

    void delete(Long id);

    RefreshToken save(RefreshToken refreshToken);

    boolean existsInDb(RefreshToken refreshToken);

}
