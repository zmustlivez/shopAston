package dao;

import entity.Order;
import entity.Product;
import entity.Shop;

import java.util.List;

public interface ShopDAO {

    void createShopTable();

    void dropShopTable();

    void clearShopTable();

    void saveShop(Shop shop);

    List<Shop> getShops();

    Shop getShopByName(String name);

    Shop getShopById(int id);

    List<Order> getOrders();

    List<Product> getProducts();

    void updateShop(Shop shop);

}
