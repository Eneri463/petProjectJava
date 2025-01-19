package com.example.petProject.controllers;

import com.example.petProject.dto.AuthResponseDTO;
import com.example.petProject.dto.LoginDTO;
import com.example.petProject.dto.RegistrationResponseDTO;
import com.example.petProject.models.Customer;
import com.example.petProject.models.Role;
import com.example.petProject.models.UserEntity;
import com.example.petProject.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc()
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
class AuthControllerTestIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ObjectMapper objectMapper;

    @BeforeAll
    void beforeAll()
    {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        this.refreshTokenRepository.deleteAll();

        Role role = roleRepository.save(new Role(1L,"USER"));
        UserEntity user = new UserEntity(1L, "Username", passwordEncoder.encode("Password_1"), Set.of(role));
        userRepository.save(user);

        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /auth/login возвращает HTTP-ответ со статусом 200 OK и токены доступа")
    void login_ReturnValidResponseEntity() throws Exception
    {
        // given
        LoginDTO loginDTO = new LoginDTO("Username", "Password_1");

        var requestBuilder = MockMvcRequestBuilders.get("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginDTO));

        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    @DisplayName("GET /auth/registration возвращает HTTP-ответ со статусом CREATED и зарегистрированного пользователя")
    void registration_ReturnValidResponseEntity() throws Exception
    {
        // given
        LoginDTO loginDTO = new LoginDTO("Username2", "Password_2");

        var requestBuilder = MockMvcRequestBuilders.get("/auth/registration").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginDTO));

        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.jsonPath("$.username").value("Username2"),
                        MockMvcResultMatchers.jsonPath("$.password").value("Password_2"));
    }






}