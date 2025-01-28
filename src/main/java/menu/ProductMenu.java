package menu;

import entities.Product;
import service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();

    public static void menu() {
        while (true) {
            System.out.println("Выберите действие для Товара:");
            System.out.println("1. Создать продукт");
            System.out.println("2. Найти продукт по ID");
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

    public static void createProduct() {
        long id = 123L;

        System.out.println("Введите имя товара:");
        String name = scanner.nextLine();

        BigDecimal price;
        while (true) {
            System.out.println("Введите цену товара");
            if (scanner.hasNextBigDecimal()) {
                price = scanner.nextBigDecimal();
                break;
            } else {
                System.out.println("Некорректный ввод ");
                scanner.next();
            }
        }
        scanner.nextLine();

        LocalDate expireDate;
        while (true) {
            System.out.println("Введите срок годности товара");
            expireDate = LocalDate.parse(scanner.next());
            break;
        }
        scanner.nextLine();

        productService.create(id, name, price, expireDate);
        System.out.println("Продукт с id: " + id + " создан");
    }

    private static void findProductById() {
        System.out.println("Введите id продукта:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productService.getById(id);
        if (product != null) {
            System.out.println("Найден продукт: " + product);
        } else {
            //не совсем корректно. возможно, была ошибка в бд. надо тогда исключение в этом методы авызвать
            System.out.println("Продукт с id " + id + " не найден");
        }
    }

    private static void findProductByName() {
        System.out.println("Введите наименование продукта:");
        String name = scanner.next();
        scanner.nextLine();

        List<Product> products = productService.getByName(name);
        if (products != null) {
            System.out.println("Найдены продукт: ");//TODO
            //распечатать список продуктов
        } else {
            //не совсем корректно. возможно, была ошибка в бд. надо тогда исключение в этом методы авызвать
            System.out.println("Продукты с наименованием " + name + " не найдены");
        }
    }

    private static void findAllProducts() {
        System.out.println("Список всех продуктов:");
        productService.findAllProducts().forEach(System.out::println);
    }

    private static void updateProduct() {
        System.out.println("Введите id продукта для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productService.getById(id);
        if (product == null) {
            System.out.println("Продукт с id " + id + " не найден");
            return;
        }
        System.out.println("Текущая информация о продукте:" + product);

        System.out.println("Введите новое наименование продукта:");
        String newName = scanner.nextLine();

        System.out.println("Введите новую цену");
        BigDecimal newPrice = scanner.nextBigDecimal();

        System.out.println("Введите новый срок годности");
        LocalDate newExpireDate = LocalDate.parse(scanner.next());

        productService.update(new Product(id, newName, newPrice, newExpireDate));
        System.out.println("Товар обновлен: " + product);
    }

    private static void deleteProduct() {
        System.out.println("Введите id продукта для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        productService.delete(id);
        System.out.println("Покупатель с ID " + id + " удален");
    }


}
