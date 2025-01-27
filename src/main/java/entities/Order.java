package entities;

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
    private long buyer_id;
//    private List<Product> products;
    private long shop_id;
}
