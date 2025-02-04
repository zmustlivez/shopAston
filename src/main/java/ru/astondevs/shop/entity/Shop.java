package ru.astondevs.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Даниил
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

//    @OneToMany() не удалось связать таблицы выдает ошибку
//    private List<Order> orderList;

//    @ManyToMany
//    private List<Product> productList;
}
