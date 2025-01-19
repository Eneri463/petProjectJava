package com.example.petProject.controllers;

import com.example.petProject.dto.AuthResponseDTO;
import com.example.petProject.dto.LoginDTO;
import com.example.petProject.dto.RegistrationResponseDTO;
import com.example.petProject.models.Order;
import com.example.petProject.models.Role;
import com.example.petProject.models.UserEntity;
import com.example.petProject.services.AuthServiceInterface;
import com.example.petProject.services.RefreshTokenServiceInterface;
import com.example.petProject.services.RoleServiceInterface;
import com.example.petProject.services.impl.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTestUnit {

    @Mock
    private AuthServiceInterface authService;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleServiceInterface roleService;
    @Mock
    private RefreshTokenServiceInterface refreshTokenService;

    @InjectMocks
    AuthController controller;

    @Test
    @DisplayName("GET /auth/login возвращает HTTP-ответ со статусом 200 OK и токены доступа")
    void login_ReturnValidResponseEntity()
    {
        // given
        LoginDTO loginDTO = new LoginDTO("Username", "Password_1");
        UserEntity user = new UserEntity(1L, loginDTO.getUsername(), loginDTO.getPassword(), Set.of(new Role(1L,"USER")));

        var res = new AuthResponseDTO("accessToken", "refreshToken");

        Mockito.doReturn(user).when(this.customUserDetailsService).loadUser(loginDTO.getUsername());
        Mockito.doReturn(res)
                .when(this.authService).authenticate(loginDTO.getUsername(),loginDTO.getPassword());


        // when
        var response = this.controller.login(loginDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(res, response.getBody());
    }

    @Test
    @DisplayName("GET /auth/registration возвращает HTTP-ответ со статусом CREATED и зарегистрированного пользователя")
    void registration_ReturnValidResponseEntity()
    {
        // given
        Role role = new Role(1L,"USER");
        LoginDTO loginDTO = new LoginDTO("Username", "Password_1");
        UserEntity user = new UserEntity(1L, loginDTO.getUsername(), "encodePassword", Set.of(role));

        var expected = new RegistrationResponseDTO(1L, loginDTO.getUsername(),loginDTO.getPassword());

        Mockito.doReturn(false).when(this.customUserDetailsService).ifUserExists(loginDTO.getUsername());
        Mockito.doReturn("encodePassword").when(this.passwordEncoder).encode(loginDTO.getPassword());
        Mockito.doReturn(user).when(this.customUserDetailsService).save(Mockito.any(UserEntity.class));
        Mockito.doReturn(role).when(this.roleService).findByName("USER");

        // when
        var response = this.controller.registration(loginDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("GET /auth/registration возвращает HTTP-ответ со статусом 500 INTERNAL SERVER ERROR")
    void registration_ReturnError500ResponseEntity()
    {
        // given
        Role role = new Role(1L,"USER");
        LoginDTO loginDTO = new LoginDTO("Username", "Password_1");
        UserEntity user = new UserEntity(1L, loginDTO.getUsername(), "encodePassword", Set.of(role));

        Mockito.doReturn(false).when(this.customUserDetailsService).ifUserExists(loginDTO.getUsername());
        Mockito.doReturn("encodePassword").when(this.passwordEncoder).encode(loginDTO.getPassword());
        Mockito.doReturn(null).when(this.roleService).findByName("USER");

        // then
        assertThrows(ResponseStatusException.class, () -> this.controller.registration(loginDTO));
    }

    @Test
    @DisplayName("GET /auth/registration возвращает HTTP-ответ со статусом 400")
    void registration_ReturnError400ResponseEntity()
    {
        // given
        LoginDTO loginDTO = new LoginDTO("Username", "Password_1");

        Mockito.doReturn(true).when(this.customUserDetailsService).ifUserExists(loginDTO.getUsername());

        // then
        assertThrows(ResponseStatusException.class, () -> this.controller.registration(loginDTO));
        Mockito.verifyNoInteractions(this.passwordEncoder);
        Mockito.verifyNoInteractions(this.roleService);
    }


}