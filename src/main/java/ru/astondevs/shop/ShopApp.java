package ru.astondevs.shop;

import ru.astondevs.shop.controller.BuyerMenu;
import ru.astondevs.shop.controller.OrderMenu;
import ru.astondevs.shop.controller.ProductMenu;
import ru.astondevs.shop.controller.ShopMenu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Класс {@link ShopApp} представляет собой точку входа в Spring Boot приложение.
 * Реализует интерфейс {@link CommandLineRunner}, что позволяет выполнять код после запуска приложения.
 * Класс предоставляет консольное меню для взаимодействия с пользователем и управления сущностями:
 * <ul>
 *     <li>Покупатели</li>
 *     <li>Заказы</li>
 *     <li>Продукты</li>
 *     <li>Магазин</li>
 * </ul>
 *
 * @see SpringBootApplication
 * @see CommandLineRunner
 */
@SpringBootApplication
public class ShopApp implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);

    private final BuyerMenu buyerMenu;

    /**
     * Конструктор класса {@link ShopApp}. Внедряет зависимость {@link BuyerMenu} для работы с покупателями.
     *
     * @param buyerMenu меню для управления покупателями.
     */
    public ShopApp(BuyerMenu buyerMenu) {
        this.buyerMenu = buyerMenu;
    }

    /**
     * Точка входа в приложение. Запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(ShopApp.class, args);
    }

    /**
     * Метод, выполняющийся после запуска приложения. Предоставляет пользователю консольное меню
     * для выбора сущности и выполнения соответствующих действий.
     *
     * @param args аргументы командной строки (не используются).
     */
    @Override
    public void run(String... args) {

        final OrderMenu orderMenu = new OrderMenu();
        final ProductMenu productMenu = new ProductMenu();

        while (true) {
            System.out.println("Выберите сущность:");
            System.out.println("1. Покупатель");
            System.out.println("2. Заказ");
            System.out.println("3. Продукт");
            System.out.println("4. Магазин");
            System.out.println("5. Выход");
            int entityChoice = scanner.nextInt();
            scanner.nextLine();

            switch (entityChoice) {
                case 1:
                    buyerMenu.menu();
                    break;
                case 2:
                    orderMenu.menu();
                    break;
                case 3:
                    productMenu.menu();
                    break;
                case 4:
                    ShopMenu.Menu();
                    break;
                case 5:
                    System.out.println("Выход из программы.");
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
