package dao;

import entity.Order;
import entity.Shop;

import java.util.List;

public interface ShopDAO {



    void clearTable();

    void save(Shop shop);

    List<Shop> getAll();

    Shop read(long id);


    void update(Shop shop);

    Shop getById(long id);

    List<Order> getOrders();

}
