package dao;

import entity.Order;
import entity.Shop;

import java.util.List;

public interface ShopDAO {



    void clearTable();

    void create(Shop shop);

    List<Shop> getAll();

    Shop read(long id);

void delete(long id);

    void update(Shop shop);


    List<Order> getOrders();

}
