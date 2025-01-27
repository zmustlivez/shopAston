package dao;

import entity.Buyer;

import java.util.List;

public interface BuyerDAO {

    Buyer createBuyer(Buyer buyer);

    Buyer readBuyer(long id);

    void updateBuyer(Buyer buyer);

    void deleteBuyer(long id);

    List<Buyer> findAllBuyers();

}
