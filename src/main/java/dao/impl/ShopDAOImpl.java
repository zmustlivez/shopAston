package dao.impl;

import dao.ShopDAO;
import entity.Order;
import entity.Shop;

import java.util.List;

public class ShopDAOImpl implements ShopDAO {
    @Override
    public void clearTable() {

    }

    @Override
    public void create(Shop shop) {

    }

    @Override
    public List<Shop> getAll() {
        return List.of();
    }

    @Override
    public Shop read(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Shop shop) {

    }

    @Override
    public List<Order> getOrders() {
        return List.of();
    }
}
