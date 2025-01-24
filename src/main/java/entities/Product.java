package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long id;
    private long price;
    private String name;
    private LocalDate expiryDate;
    private Shop shop;
}
