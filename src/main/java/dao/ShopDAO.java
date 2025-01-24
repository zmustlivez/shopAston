package dao;

import entities.Order;
import entities.Shop;

import java.util.List;

public interface ShopDAO {

    void createShopTable();

    void dropShopTable();

    void clearShopTable();

    void saveShop(Shop shop);

    List<Shop> getShops();

    List<Order> getOrders();

    void updateShop(Shop shop);

    Shop getShopById(int id);

}
