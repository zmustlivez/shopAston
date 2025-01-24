package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {

    void createProductTable();

    void dropProductTable();

    void clearProductTable();

    void saveProduct(Product product);

    List<Product> getShops();

    void updateProduct(Product product);

    Product getShopById(int id);

    List<Product> getAllProducts();
}
