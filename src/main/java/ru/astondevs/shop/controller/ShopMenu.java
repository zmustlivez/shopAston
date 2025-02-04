package ru.astondevs.shop.controller;


import org.springframework.stereotype.Component;
import ru.astondevs.shop.service.ShopService;

import java.util.Scanner;
    /**
     * Класс, реализующий пользовательское меню для взаимодействия с сервисом магазинов.
     * Позволяет выполнять CRUD-операции через консольный интерфейс.
     */
    @Component
    public class ShopMenu {

        private final Scanner scanner = new Scanner(System.in);
        private final ShopService shopService;

        /**
         * Конструктор для внедрения зависимости сервиса магазинов.
         *
         * @param shopService сервис для работы с магазинами
         */

        public ShopMenu(ShopService shopService) {
            this.shopService = shopService;
        }

        /**
         * Отображает консольное меню и обрабатывает выбор пользователя.
         * Предоставляет следующие опции:
         * 1. Создать магазин
         * 2. Получить магазин по ID
         * 3. Обновить магазин
         * 4. Удалить магазин
         * 5. Показать все магазины
         * 6. Вернуться в главное меню
         */
        public void Menu() {
            while (true) {

                System.out.println("Выберите действие для Магазина:");
                System.out.println("1. Создать магазин");
                System.out.println("2. Получить магазин по id");
                System.out.println("3. Обновить магазин");
                System.out.println("4. Удалить магазин");
                System.out.println("5. Получить все магазины в таблице");
                System.out.println("6. Вернуться в главное меню");

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
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                        return;
                }
            }
        }
    }
