package ru.astondevs.shop.controller;

import ru.astondevs.shop.service.OrderServiceMenu;

import java.util.Scanner;

public class OrderMenu {

    static Scanner scanner = new Scanner(System.in);

    private final OrderServiceMenu orderServiceMenu = new OrderServiceMenu();

    public void menu() {
        while (true) {
            System.out.printf("Введите цифру означающую действие\n" +
                    "1 - Записать заказ в таблицу с заказами (Создать заказ)\n" +
                    "2 - Внести изменения в заказ (потребуется id заказа)\n" +
                    "3 - Удалить заказ (потребуется id заказа)\n" +
                    "4 - Найти заказ (потребуется id заказа)\n" +
                    "5 - Получить список всех заказов из таблицы\n" +
                    "6 - Найти заказ по id покупателя\n" +
                    "7 - Вернуться в главное меню\n");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    orderServiceMenu.create();
                    break;
                case 2:
                    orderServiceMenu.update();
                    break;
                case 3:
                    orderServiceMenu.delete();
                    break;
                case 4:
                    orderServiceMenu.read();
                    break;
                case 5:
                    orderServiceMenu.findAll();
                    break;
                case 6:
                    orderServiceMenu.findOrderByBuyerId();
                    break;
                case 7:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
