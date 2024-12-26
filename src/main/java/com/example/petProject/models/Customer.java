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
    @Min(1)
    private Long id;

    @Pattern(regexp ="^[А-ЯЕЁ][а-яеё]{0,19}$")
    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp ="^[А-ЯЕЁ][а-яеё]{0,19}$")
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp ="^[78][0-9]{10}$")
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp ="^[а-яеёА-ЯЕЁ0-9][а-яеёА-ЯЕЁ0-9\\-\\.\b]{0,29}$")
    @NotNull
    @Column(name = "street", length = 30)
    private String street;

    @Pattern(regexp ="(^[1-9][0-9]{0,4}[а-яеёА-ЯЕЁ]?$)|(^[1-9][0-9]{0,4}\\/[1-9][0-9]{0,4}$)")
    @NotNull
    @Column(name = "house")
    private String house;

    @NotNull()
    @Min(value = 1)
    @Column(name = "apartment")
    private int apartment;


    public Customer(String firstName, String lastName, String phoneNumber,String street, String house,int apartment)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }
}
