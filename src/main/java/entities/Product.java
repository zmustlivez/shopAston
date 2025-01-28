package entities;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {
    //идентификатор
    private long id;
    //наименование
    private String name;
    //цена
    private BigDecimal price;
    //срок годности
    private LocalDate expiryDate;

    public Product(long id, String name, BigDecimal price, LocalDate expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
