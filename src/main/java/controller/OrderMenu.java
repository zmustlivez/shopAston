package controller;

import service.OrderServiceMenu;

import java.util.Scanner;

public class OrderMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        while (true) {
/*            System.out.println("Выберите действие для Заказа:");
            System.out.println("1. Создать заказ");
//            System.out.println("2. Найти покупателя по ID");
//            System.out.println("3. Обновить покупателя");
//            System.out.println("4. Удалить покупателя");
//            System.out.println("5. Найти всех покупателей");
            System.out.println("6. Найти заказы покупателя");
            System.out.println("7. Вернуться в главное меню");*/
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
                    OrderServiceMenu.create();
                    break;
                case 2:
                    //OrderServiceMenu.update();
                    break;
                case 3:
                    OrderServiceMenu.delete();
                    break;
                case 4:
                    OrderServiceMenu.read();
                    break;
                case 5:
                    OrderServiceMenu.findAll();
                    break;
                case 6:
                    OrderServiceMenu.findOrderByBuyerId();
                    break;
                case 7:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
