package controller;

import service.ProductService;

import java.util.Scanner;

public class ProductMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductService();

    public void menu() {
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
                    productService.createTable();
                    break;
                case 2:
                    productService.dropTable();
                    break;
                case 3:
                    productService.createProduct();
                    break;
                case 4:
                    productService.findProductById();
                    break;
                case 5:
                    productService.findProductByName();
                    break;
                case 6:
                    productService.findAllProducts();
                    break;
                case 7:
                    productService.updateProduct();
                    break;
                case 8:
                    productService.deleteProduct();
                    break;
                case 9:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
