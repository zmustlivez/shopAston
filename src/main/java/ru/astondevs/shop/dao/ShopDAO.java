package ru.astondevs.shop.dao;

import ru.astondevs.shop.entity.Order;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.entity.Shop;

import java.util.List;

public interface ShopDAO {

    void createShopTable();

    void dropShopTable();

    void clearShopTable();

    void delete(long id);

    void create(Shop shop);

    List<Shop> findAll();

    Shop getShopByName(String name);

    Shop read(long id);

    List<Order> getOrders();

    List<Product> getProducts();

    void update(Shop shop);

}
