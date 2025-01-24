package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long id;
    private long price;
    private List<Product> products;
    private Shop shop;
}
