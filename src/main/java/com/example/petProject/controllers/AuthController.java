package com.example.petProject.controllers;

import com.example.petProject.dto.AuthResponseDTO;
import com.example.petProject.dto.LoginDTO;
import com.example.petProject.dto.RegistrationResponseDTO;
import com.example.petProject.models.Role;
import com.example.petProject.models.UserEntity;
import com.example.petProject.services.RoleServiceInterface;
import com.example.petProject.services.impl.AuthService;
import com.example.petProject.services.impl.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@EnableAutoConfiguration
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;
    CustomUserDetailsService customUserDetailsService;
    PasswordEncoder passwordEncoder;
    RoleServiceInterface roleService;

    // ------------------------------------------------------------

    // авторизация пользователя
    @GetMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO)
    {
        //todo
        // надо ли сначала проверить, есть ли такой логин

        AuthResponseDTO tokens = authService.authenticate(loginDTO.getUsername(),loginDTO.getPassword());

        return ResponseEntity.ok(tokens);
    }


    // регистрация пользователя
    @GetMapping("registration")
    public ResponseEntity<RegistrationResponseDTO> registration(@Valid @RequestBody LoginDTO loginDTO)
    {
        if (customUserDetailsService.ifUserExists(loginDTO.getUsername()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is taken");
        }

        UserEntity user = new UserEntity();
        user.setUsername(loginDTO.getUsername());
        user.setPassword(passwordEncoder.encode((loginDTO.getPassword())));

        Role role = roleService.findByName("USER");

        if (role == null)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        user.addRole(role);

        Long id = customUserDetailsService.save(user).getId();

        return new ResponseEntity<>(new RegistrationResponseDTO(id, loginDTO.getUsername(),loginDTO.getPassword()), HttpStatus.CREATED);

    }

}
