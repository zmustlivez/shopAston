package dao;

import entity.Buyer;
import entity.Order;

import java.util.List;

public interface BuyerDAO {

    void createBuyer(Buyer buyer);

    void updateOrder(Buyer buyer);

    void deleteBuyer(int id);

    Order findBuyerById(int id);

    List<Buyer> findAllBuyers();


}
