package ru.astondevs.shop.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.repository.ProductRepository;
import ru.astondevs.shop.service.ProductService;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Добавление продукта в таблицу
     *
     * @param product - продукт
     * @return добавленный продукт или null, если неудачно
     */
    @Override
    public Product create(Product product) {
        log.info("Добавление продукта " + product);
        return productRepository.save(product);
    }

    /**
     * Поиск продуктов по id.
     *
     * @param id - идентификатор продукта
     * @return - информацию о продукте, если он найден и null если такого
     * продукта нет в бд
     */
    @Override
    public Product findById(Long id) {
        log.info("Поиск продукта по id: " + id);
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Поиск товаров, начинающихся на префикс
     *
     * @param prefix - наименование товара
     * @return список товаров с указанным наименованием
     */
    @Override
    public List<Product> findByNameStartingWith(String prefix) {
        List<Product> products = productRepository.findByNameStartingWith(prefix, Sort.by(Sort.Order.asc("name")));
        log.info("Найдено " + products.size() + " продуктов, начинающихся на " + prefix);
        return products;
    }

    /**
     * Получение списка всех продуктов
     *
     * @return список всех продуктов
     */
    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Найдено " + products.size() + "продуктов");
        return products;
    }

    /**
     * Обновление информации о продукте
     *
     * @param product - продукт
     * @return - количество обновленных записей (если равно нулю, то запись с таким идентификатором не нашлась)
     */
    @Override
    public Product update(Product product) {
        Product updatedProduct = productRepository.save(product);
        log.info("Продукт " + product.getId() + "успешно обновлен");
        return updatedProduct;
    }

    /**
     * Удаление продукта по его идентификатору
     *
     * @param id - идентификатор продукта
     */
    @Override
    public void deleteById(Long id) {
        log.info("Удаление продукта по идентификатору " + id);
        productRepository.deleteById(id);
    }
}
