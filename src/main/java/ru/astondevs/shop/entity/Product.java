package ru.astondevs.shop.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {
    /**
     * идентификатор продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * наименование продукта
     */
    @Column(name = "name")
    private String name;

    /**
     * цена продукта
     */
    @Column(name = "price")
    private BigDecimal price;


    /**
     * срок годности продукта
     */
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToMany(fetch = FetchType.LAZY)//, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JoinTable(name = "product_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> order;

    public Product(String name, BigDecimal price, LocalDate expiryDate) {
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Product {" +
                " id = " + id +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", expiryDate = " + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(expiryDate, product.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, expiryDate);
    }
}
