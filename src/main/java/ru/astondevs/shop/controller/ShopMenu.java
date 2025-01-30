package ru.astondevs.shop.controller;


import ru.astondevs.shop.dao.impl.ShopDAOImpl;
import ru.astondevs.shop.entity.Shop;

import java.util.Scanner;

public class ShopMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void Menu() {
        ShopDAOImpl shopDAO = new ShopDAOImpl();
        while (true) {
            System.out.println("Выберите действие для Магазина:");
            System.out.println("1. Создать таблицу магазина");
            System.out.println("2. Удалить таблицу магазина");
            System.out.println("3. Очистить таблицу магазина");
            System.out.println("4. Создать магазин");
            System.out.println("5. Получить все магазины в таблице");
            System.out.println("6. Получить магазин по имени");
            System.out.println("7. Получить магазин по id");
            System.out.println("8. Обновить магазин");
            System.out.println("9. Получить заказы магазина");//не готово
            System.out.println("10. Получить продукты магазина");//не готово
            System.out.println("11. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    shopDAO.createShopTable();
                    break;
                case 2:
                    shopDAO.dropShopTable();
                    break;
                case 3:
                    shopDAO.clearShopTable();
                    break;
                case 4:
                    System.out.println("Введите имя магазина:");
                    shopDAO.create(new Shop(0, scanner.nextLine())
                    );
                    break;
                case 5:
                    System.out.println(shopDAO.findAll().toString());
                    break;
                case 6:
                    System.out.println("Введите имя магазина:");
                    System.out.println(shopDAO.getShopByName(scanner.nextLine()));
                    break;

                case 7:
                    System.out.println("Введите id");
                    System.out.println(shopDAO.read(scanner.nextLong()));
                    break;

                case 8:
                    System.out.println("Введите id и Имя магазина");
                    shopDAO.update(new Shop(scanner.nextInt(), scanner.nextLine()));
                    break;
                case 9:
                    return;//не готово
                case 10:
                    return;//не готово
                case 11:
                    return; //главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
