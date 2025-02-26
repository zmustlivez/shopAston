package ru.astondevs.shop.controller;

import org.springframework.stereotype.Component;
import ru.astondevs.shop.entity.Order;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.service.ProductService;

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
        //TODO возможно эти операции одинаковые.Можно вынести в util типа getString(message)

        String name = inputName("Введите имя товара:");

        BigDecimal price = inputPrice("Введите цену товара:");

        LocalDate expireDate = inputDate("Введите срок годности товара:");

        System.out.println("Добавлен продукт: " + productService.create(new Product(name, price, expireDate)));
    }

    /**
     * Ввод имени. Метод выводит сообщение message и с консоли забирает строку
     *
     * @param message - сообщение, которое выводится на консоль для указания действия
     * @return строка пользователя с консоли
     */
    private String inputName(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Ввод цены. Метод выводит сообщение message и с консоли забирает число
     *
     * @param message - сообщение, которое выводится на консоль для указания действия
     * @return введенное пользователем число(BigDecimal)
     */
    private BigDecimal inputPrice(String message) {
        System.out.println(message);
        BigDecimal result = scanner.nextBigDecimal();
        scanner.nextLine();
        return result;
    }

    /**
     * Ввод даты. Метод выводит сообщение message и с консоли забирает дату
     *
     * @param message - сообщение, которое выводится на консоль для указания действия
     * @return введенная пользователем дата(LocalDate, формат yyyy-MM-dd)
     */
    private LocalDate inputDate(String message) {
        System.out.println(message);
        return LocalDate.parse(scanner.nextLine());
    }

    /**
     * Ввод числа. Метод выводит сообщение message и с консоли забирает число
     *
     * @param message - сообщение, которое выводится на консоль для указания действия
     * @return введенное пользователем число(long)
     */
    private long inputId(String message) {
        System.out.println(message);
        long result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }

    /**
     * Поиск продукта по id
     */
    public void findProductById() {
        long id = inputId("Введите id продукта:");

        System.out.println("Найден продукт: " + productService.findById(id));
    }

    /**
     * Поиск продукта по наименованию
     */
    public void findProductByName() {
        String name = inputName("Введите наименование продукта:");

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
        long id = inputId("Введите id продукта для обновления:");

        Product product = productService.findById(id);
        if (product == null) {
            System.out.println("Продукт с id " + id + " не найден");
            return;
        }
        System.out.println("Текущая информация о продукте:" + product);

        String newString = inputName("Введите новое наименование продукта или пропустите этот шаг:");
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
        long id = inputId("Введите id продукта для удаления:");

        productService.deleteById(id);
        System.out.println("Продукт с id " + id + " удален");
    }
}
