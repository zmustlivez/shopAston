package service;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import entities.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProductService {

    private final ProductDAO repository = new ProductDAOImpl();

    public void create(Long id,
                       String name,
                       BigDecimal price,
                       LocalDate expireDate) {
        //Если существует такой продукт, то выдаем сообщение об ошибке
        if (repository.getById(id) == null) {
            Product product = new Product(id, name, price, expireDate);
            if (repository.create(product))
                System.out.println("Продукт " + product.getName() + " добавлен в базу данных");
        } else {
            System.out.println("Продукт с идентификатором " + id + "уже существует");
        }
    }

    public Product getById(Long id) {
        return repository.getById(id);
    }


    public List<Product> getByName(String name) {
        return repository.getByName(name);
    }

    public List<Product> findAllProducts() {
        return repository.getAllProducts();
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(long id) {
        repository.delete(id);
    }
}
