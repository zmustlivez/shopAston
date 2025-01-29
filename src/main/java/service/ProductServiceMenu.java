package service;

import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProductServiceMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private ProductDAO productRepository = new ProductDAOImpl();

    /**
     * Создание таблицы продуктов product (id, name, price, expiry_date)
     */
    public void createTable() {
        productRepository.createTable();
    }

    /**
     * Удаление таблицы продуктов
     */
    public void dropTable() {
        productRepository.dropTable();
    }

    /**
     * Вставка нового продукта
     */
    public void createProduct() {
        System.out.println("Введите имя товара:");
        String name = scanner.nextLine();

        System.out.println("Введите цену товара:");
        BigDecimal price = scanner.nextBigDecimal();

        scanner.nextLine();
        System.out.println("Введите срок годности товара:");
        LocalDate expireDate = LocalDate.parse(scanner.nextLine());
        //scanner.nextLine();
        productRepository.create(new Product(name, price, expireDate));
    }

    /**
     * Поиск продукта по id
     */
    public void findProductById() {
        System.out.println("Введите id продукта:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productRepository.getById(id);
        if (product != null) {
            System.out.println("Найден продукт: " + product);
        } else {
            //не совсем корректно. возможно, была ошибка в бд. надо тогда исключение в этом методы авызвать
            System.out.println("Продукт с id " + id + " не найден");
        }
    }

    /**
     * Поиск продукта по наименованию
     */
    public void findProductByName() {
        System.out.println("Введите наименование продукта:");
        String name = scanner.next();
        scanner.nextLine();

        List<Product> products = productRepository.getByName(name);
        if (products != null) {
            System.out.println("Найдены продукты: ");
            products.forEach(System.out::println);
            //распечатать список продуктов
        } else {
            //не совсем корректно. возможно, была ошибка в бд. надо тогда исключение в этом методы авызвать
            System.out.println("Продукты с наименованием " + name + " не найдены");
        }
    }

    /**
     * Вывод всех продуктов
     */
    public void findAllProducts() {
        System.out.println("Список всех продуктов:");
        productRepository.getAllProducts().forEach(System.out::println);
    }

    /**
     * Обновление информации о продукте. Если информация не изменилась,
     * обращение к БД не происходит
     */
    public void updateProduct() {
        System.out.println("Введите id продукта для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productRepository.getById(id);
        if (product == null) {
            System.out.println("Продукт с id " + id + " не найден");
            return;
        }
        System.out.println("Текущая информация о продукте:" + product);

        System.out.println("Введите новое наименование продукта или пропустите этот шаг:");
        String newString = scanner.nextLine();

        //если пользователь хочет оставить имя прежним
        String newName = newString.equals("") ? product.getName() : newString;

        System.out.println("Введите новую цену или пропустите этот шаг:");
        newString = scanner.nextLine();
        BigDecimal newPrice = newString.equals("") ? product.getPrice()
                : BigDecimal.valueOf(Long.valueOf(newString));

        System.out.println("Введите новый срок годности или пропустите этот шаг:");
        newString = scanner.nextLine();
        LocalDate newExpiryDate = newString.equals("") ? product.getExpiryDate()
                : LocalDate.parse(newString);

        Product newProduct = new Product(id, newName, newPrice, newExpiryDate);
        //если продукт не изменили, то его и не надо обновлять
        if (!product.equals(newProduct)) productRepository.update(newProduct);
        System.out.println("Товар обновлен: " + newProduct);
    }

    /**
     * Удаление продукта
     */
    public void deleteProduct() {
        System.out.println("Введите id продукта для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        productRepository.delete(id);
        System.out.println("Продукт с id " + id + " удален");
    }

}
