package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    private long id;
    private String name;
    private List<Order> orderList;
    private List<Product> productList;
}
