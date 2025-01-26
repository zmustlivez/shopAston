package dao;

import entity.Buyer;
import entity.Order;

import java.util.List;
import java.util.Optional;

public interface BuyerDAO {

    Buyer createBuyer(Buyer buyer);

    Buyer readBuyer(long id);

    Buyer updateBuyer(Buyer buyer);

    void deleteBuyer(long id);

    List<Buyer> findAllBuyers();

    Optional<Order> findOrderByBuyerId(long id);

}
