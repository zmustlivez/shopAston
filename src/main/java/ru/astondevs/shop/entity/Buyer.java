package ru.astondevs.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс {@link Buyer} представляет собой сущность покупателя.
 * Используется для хранения информации о покупателях в базе данных.
 * Аннотация {@link Entity} указывает, что класс является JPA-сущностью и связан с таблицей "buyers".
 *
 * @see Entity
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 */
@Entity(name = "buyers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Buyer {

    /**
     * Уникальный идентификатор покупателя.
     * Генерируется автоматически при добавлении записи в базу данных.
     *
     * @see Id
     * @see GeneratedValue
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Имя покупателя.
     * Хранится в колонке "name" таблицы "buyers".
     *
     * @see Column
     */
    @Column(name = "name")
    private String name;

    /**
     * Номер карты покупателя.
     * Хранится в колонке "card_number" таблицы "buyers".
     *
     * @see Column
     */
    @Column(name = "card_number")
    private long cardNumber;

    /**
     * Сумма скидки покупателя.
     * Хранится в колонке "sale_value" таблицы "buyers".
     *
     * @see Column
     */
    @Column(name = "sale_value")
    private long saleValue;
}
