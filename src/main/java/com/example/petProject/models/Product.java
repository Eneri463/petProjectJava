package com.example.petProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @Min(1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Pattern(regexp ="^[а-яеёА-ЯЕЁ0-9][а-яеёА-ЯЕЁ0-9\\-\\.\b]{0,29}$")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    @Min(value = 0)
    private double price;

    public Product(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

}
