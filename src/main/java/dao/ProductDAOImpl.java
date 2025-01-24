package dao;

import entities.Product;
import config.Config;

import java.sql.Connection;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void createProductTable() {
    }

    @Override
    public void dropProductTable() {

    }

    @Override
    public void clearProductTable() {

    }

    @Override
    public void saveProduct(Product product) {

    }

    @Override
    public List<Product> getShops() {
        return List.of();
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public Product getShopById(int id) {
        return null;
    }
}
