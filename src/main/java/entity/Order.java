package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//Виктор
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private long id;
    private Buyer buyer;
    private Shop shop;
    private Product product;
//    private List<Product> products;
}
