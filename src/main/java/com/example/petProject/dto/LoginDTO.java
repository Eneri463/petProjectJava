package com.example.petProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {

    // todo
    // нужен ли NotNull, проверить паттерн в пароле

    @NotNull
    @Pattern(regexp ="^[А-ЯЕЁа-яеёA-Za-z][А-ЯЕЁа-яеёA-Za-z0-9_]{4,29}$")
    String username;

    // todo
    @NotNull
    //@Pattern(regexp ="^[[:graph:]А-ЯЕЁа-яеё]{5,30}$")
    //@Pattern(regexp = " ^(?=[0-9])(?=[a-zа-яеё])(?=[A-ZА-ЯЕЁ]).{5,30}$")
    String password;

}
