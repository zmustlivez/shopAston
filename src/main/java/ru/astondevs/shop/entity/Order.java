package ru.astondevs.shop.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Виктор

@Entity
@Table(name = "orders", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = false)
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    @Column(nullable = false)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    public void addProduct(Product product) {
        this.products.add(new Product());
    }

}
