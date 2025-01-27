package dao;

import entities.Product;

import java.util.List;

public interface ProductDAO {

    void createProductTable();

    void dropProductTable();

    void clearProductTable();

    void saveProduct(Product product);

    List<Product> getShops();

    void updateProduct(Product product);

    Product getShopById(long id);

    List<Product> getAllProducts();
}
