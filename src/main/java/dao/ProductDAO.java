package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {

    void createTable();

    void dropTable();

    void clearTable();

    boolean create(Product product);

    Product getById(Long id);

    List<Product> getByName(String name);

    int update(Product product);

    List<Product> getAllProducts();

    int delete(long id);
}
