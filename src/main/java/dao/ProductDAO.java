package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {


    void clearTable();

    Product create(Product product);

    List<Product> getAll();

    void update(Product product);

    void delete(long id);

    Product read(long id);

}
