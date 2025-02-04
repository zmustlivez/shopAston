package ru.astondevs.shop.controller;


import ru.astondevs.shop.service.ShopService;

import java.util.Scanner;

public class ShopMenu {

    static Scanner scanner = new Scanner(System.in);
    private static ShopService shopService;

    public ShopMenu(ShopService shopService) {
        this.shopService = shopService;
    }

    public static void Menu() {
        while (true) {

            System.out.println("Выберите действие для Магазина:");
            System.out.println("1. Создать магазин");
            System.out.println("2. Получить магазин по id");
            System.out.println("3. Обновить магазин");
            System.out.println("4. Удалить магазин");
            System.out.println("5. Получить все магазины в таблице");
            System.out.println("6. Получить заказы магазина");//не готово
            System.out.println("7. Получить продукты магазина");//не готово
            System.out.println("8. Вернуться в главное меню");

            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    shopService.create();
                    break;
                case 2:
                    shopService.read();
                    break;
                case 3:
                    shopService.update();
                    break;
                case 4:
                    shopService.delete();
                    break;
                case 5:
                    shopService.findAll();
                    break;
                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    return;
            }
        }
    }

}
