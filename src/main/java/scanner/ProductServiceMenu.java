package scanner;

import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProductServiceMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static ProductDAO productRepository = new ProductDAOImpl();

    public static void menu() {
        while (true) {
            System.out.println("Выберите действие для Товара:");
            System.out.println("1. Создать таблицу продуктов");
            System.out.println("2. Удалить таблицу продуктов");
            System.out.println("3. Создать продукт");
            System.out.println("4. Найти продукт по id");
            System.out.println("5. Найти продукт по наименованию");
            System.out.println("6. Вывести список всех продуктов");
            System.out.println("7. Обновить информацию о продукте");
            System.out.println("8. Удалить продукт");
            System.out.println("9. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    createTable();
                    break;
                case 2:
                    dropTable();
                    break;
                case 3:
                    createProduct();
                    break;
                case 4:
                    findProductById();
                    break;
                case 5:
                    findProductByName();
                    break;
                case 6:
                    findAllProducts();
                    break;
                case 7:
                    updateProduct();
                    break;
                case 8:
                    deleteProduct();
                    break;
                case 9:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    public static void createTable(){
        productRepository.createTable();
    }

    public static void dropTable(){
        productRepository.dropTable();
    }

    public static void createProduct() {
        long id = 123L;

        System.out.println("Введите имя товара:");
        String name = scanner.nextLine();

        System.out.println("Введите цену товара");
        BigDecimal price = scanner.nextBigDecimal();

        scanner.nextLine();
        System.out.println("Введите срок годности товара");
        LocalDate expireDate =LocalDate.parse(scanner.nextLine());
        //scanner.nextLine();
        productRepository.create(new Product (id, name, price, expireDate));
    }

    private static void findProductById() {
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

    private static void findProductByName() {
        System.out.println("Введите наименование продукта:");
        String name = scanner.next();
        scanner.nextLine();

        List<Product> products = productRepository.getByName(name);
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
        productRepository.getAllProducts().forEach(System.out::println);
    }

    private static void updateProduct() {
        System.out.println("Введите id продукта для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Product product = productRepository.getById(id);
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

        productRepository.update(new Product(id, newName, newPrice, newExpireDate));
        System.out.println("Товар обновлен: " + product);
    }

    private static void deleteProduct() {
        System.out.println("Введите id продукта для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        productRepository.delete(id);
        System.out.println("Покупатель с ID " + id + " удален");
    }


}
