package ru.astondevs.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@link Order} представляет собой сущность заказа.
 * Используется для хранения информации о заказах в базе данных.
 * Аннотация {@link Entity} указывает, что класс является JPA-сущностью и связан с таблицей "orders".
 *
 * @see Entity
 * @see Table
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 */
@Entity
@Table(name = "orders", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    /**
     * Уникальный идентификатор заказа.
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
     * Покупатель, оформивший заказ.
     * Связан с таблицей "buyers" по внешнему ключу "buyer_id".
     *
     * @see ManyToOne
     * @see JoinColumn
     */
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = false)
    private Buyer buyer;

    /**
     * Магазин, в котором был оформлен заказ.
     * Связан с таблицей "shops" по внешнему ключу "shop_id".
     *
     * @see ManyToOne
     * @see JoinColumn
     */
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    /**
     * Список товаров, включенных в заказ.
     * Связан с таблицей "products" через связь ManyToMany.
     *
     * @see ManyToMany
     * @see JoinTable
     */
    @ManyToMany(mappedBy = "order")
    private List<Product> products = new ArrayList<>();

    /**
     * Добавляет товар в заказ.
     *
     * @param product товар, который нужно добавить в заказ.
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyer=" + buyer +
                ", shop=" + shop.getName() +
                ", products=" + products +
                '}';
    }
}
