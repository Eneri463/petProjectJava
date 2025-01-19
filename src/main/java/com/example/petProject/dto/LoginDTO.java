package com.example.petProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

    @NotNull
    @Pattern(regexp ="^[А-ЯЕЁа-яеёA-Za-z][А-ЯЕЁа-яеёA-Za-z0-9_]{4,29}$")
    String username;

    @NotNull
    //@Pattern(regexp = "((?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%_\\.]).{5,})")
    String password;

}
