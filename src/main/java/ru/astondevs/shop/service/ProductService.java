package ru.astondevs.shop.service;

import org.springframework.data.domain.Sort;
import ru.astondevs.shop.entity.Product;

import java.util.List;

public interface ProductService {
    /**
     * Добавление продукта в таблицу
     *
     * @param product - продукт
     * @return добавленный продукт или null, если неудачно
     */
    Product create(Product product);

    /**
     * Поиск продуктов по id.
     *
     * @param id - идентификатор продукта
     * @return - информацию о продукте, если он найден и null если такого
     * продукта нет в бд
     */
    Product findById(Long id);

    /**
     * Поиск товаров, начинающихся на префикс
     *
     * @param prefix - наименование товара
     * @return список товаров с указанным наименованием
     */
    List<Product> findByNameStartingWith(String prefix);

    /**
     * Получение списка всех продуктов
     *
     * @return список всех продуктов
     */
    List<Product> findAllProducts();

    /**
     * Обновление информации о продукте
     *
     * @param product - продукт
     * @return - количество обновленных записей (если равно нулю, то запись с таким идентификатором не нашлась)
     */
    Product update(Product product);

    /**
     * Удаление продукта по его идентификатору
     *
     * @param id - идентификатор продукта
     */
    void deleteById(Long id);
}
