package com.example.petProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//todo
// проверить, что все ограничения на параметры класса корректные
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull()
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NotNull()
    @Column(name = "last_name", length = 50)
    private String lastName;

    //todo
    // стоит задать стандарт номера телефона
    @NotNull()
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @NotNull()
    @Column(name = "street", length = 30)
    private String street;

    //todo
    // номера домов могут быть дробными
    @NotNull()
    @Min(value = 1)
    @Column(name = "house")
    private int house;

    @NotNull()
    @Min(value = 1)
    @Column(name = "apartment")
    private int apartment;

}
