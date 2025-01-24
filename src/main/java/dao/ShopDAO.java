package dao;

import entities.Shop;

import java.util.List;

public interface ShopDAO {
    void createShopTable();
    void dropShopTable();
    void clearShopTable();
    void saveShop(Shop shop);
    List<Shop> getShops();
    void updateShop(Shop shop);
    Shop getShopById(int id);
}
