package ru.astondevs.shop.controller;

import org.springframework.stereotype.Component;
import ru.astondevs.shop.entity.Buyer;
import ru.astondevs.shop.entity.Order;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.entity.Shop;
import ru.astondevs.shop.service.impl.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ProductController {
    private final ProductService productService;

    private final Scanner scanner = new Scanner(System.in);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Консольное меню
     */
    public void menu() {
        while (true) {
            System.out.println("Выберите действие для Товара:");
            System.out.println("1. Создать продукт");
            System.out.println("2. Найти продукт по id");
            System.out.println("3. Найти продукт по наименованию");
            System.out.println("4. Вывести список всех продуктов");
            System.out.println("5. Обновить информацию о продукте");
            System.out.println("6. Удалить продукт");
            System.out.println("7. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    findProductById();
                    break;
                case 3:
                    findProductByName();
                    break;
                case 4:
                    findAllProducts();
                    break;
                case 5:
                    updateProduct();
                    break;
                case 6:
                    deleteProduct();
                    break;
                case 7:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
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
        //todo вот стоит ли условный оператор делать отдельный для вызова в случае,если продукт == null?
        System.out.println("Добавлен продукт: " + productService.create(new Product(name, price, expireDate)));
    }

    /**
     * Поиск продукта по id
     */
    public void findProductById() {
        System.out.println("Введите id продукта:");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Найден продукт: " + productService.findById(id));
    }

    /**
     * Поиск продукта по наименованию
     */
    public void findProductByName() {
        System.out.println("Введите наименование продукта:");
        String name = scanner.next();
        scanner.nextLine();

        List<Product> products = productService.findByNameStartingWith(name);
        if (products != null) {
            System.out.println("Найдены продукты: ");
            products.forEach(System.out::println);
        } else {
            System.out.println("Продукты с наименованием " + name + " не найдены");
        }
    }

    /**
     * Вывод всех продуктов
     */
    public void findAllProducts() {
        System.out.println("Список всех продуктов:");
        productService.findAllProducts().forEach(System.out::println);
    }

    /**
     * Обновление информации о продукте. Если информация не изменилась,
     * обращение к БД не происходит
     */
    public void updateProduct() {
        System.out.println("Введите id продукта для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productService.findById(id);
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

        Product newProduct = new Product(id, newName, newPrice, newExpiryDate, new ArrayList<Order>());
        //если продукт не изменили, то его и не надо обновлять
        if (!product.equals(newProduct)) productService.update(newProduct);
        System.out.println("Товар обновлен: " + newProduct);
    }

    /**
     * Удаление продукта
     */
    public void deleteProduct() {
        System.out.println("Введите id продукта для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        productService.deleteById(id);
        System.out.println("Продукт с id " + id + " удален");
    }
}
