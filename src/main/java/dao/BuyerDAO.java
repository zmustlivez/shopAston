package dao;

import entities.Buyer;
import entities.Order;

import java.util.List;

public interface BuyerDAO {

    void create(Buyer buyer);

    void update(Buyer buyer);

    void delete(int id);

    Buyer findById(long id);

    List<Buyer> findAll();


}
