package com.example.petProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Pattern(regexp ="^[А-Я][а-я]*$")
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Pattern(regexp ="^[А-Я][а-я]*$")
    @Column(name = "last_name", length = 20)
    private String lastName;

    @Pattern(regexp ="^[78][0-9]{10}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp ="^[а-яА-Я0-9]+[а-яА-Я0-9\\-,\\.\\b]*$")
    @Column(name = "street", length = 30)
    private String street;

    @Pattern(regexp ="(^[1-9]+[0-9]*[а-я]?$)|(^[1-9]+[0-9]*\\/[1-9]+[0-9]*$)")
    @Column(name = "house", length = 10)
    private String house;

    @NotNull()
    @Min(value = 1)
    @Column(name = "apartment")
    private int apartment;

}
