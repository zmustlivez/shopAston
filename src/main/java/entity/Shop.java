package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Даниил
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    private long id;
    private String name;
//    private List<Order> orderList;
//    private List<Product> productList;
}
