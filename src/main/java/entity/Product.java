package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    //идентификатор
    private long id;
    //наименование
    private String name;
    //цена
    private BigDecimal price;
    //срок годности
    private LocalDate expiryDate;

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
