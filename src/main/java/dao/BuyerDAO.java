package dao;

import entities.Buyer;
import entities.Order;

import java.util.List;

public interface BuyerDAO {

    void createBuyer(Buyer buyer);

    void updateOrder(Buyer buyer);

    void deleteBuyer(int id);

    Order findBuyerById(int id);

    List<Buyer> findAllBuyers();


}
