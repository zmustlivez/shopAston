package dao;

import entity.Buyer;

import java.util.List;

public interface BuyerDAO {

    Buyer create(Buyer buyer);

    Buyer read(long id);

    void update(Buyer buyer);

    void delete(long id);

    List<Buyer> findAll();

}
