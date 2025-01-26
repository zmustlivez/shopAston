package controller;

import service.BuyerServiceMenu;

import java.util.Scanner;

public class BuyerMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        while (true) {
            System.out.println("Выберите действие для Покупателя:");
            System.out.println("1. Создать покупателя");
            System.out.println("2. Найти покупателя по ID");
            System.out.println("3. Обновить покупателя");
            System.out.println("4. Удалить покупателя");
            System.out.println("5. Найти всех покупателей");
            System.out.println("6. Вернуться в главное меню");
//            System.out.println("6. Найти заказы покупателя");
            System.out.println("7. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    BuyerServiceMenu.create();
                    break;
                case 2:
                    BuyerServiceMenu.read();
                    break;
                case 3:
                    BuyerServiceMenu.update();
                    break;
                case 4:
                    BuyerServiceMenu.delete();
                    break;
                case 5:
                    BuyerServiceMenu.findAll();
                    break;
                case 6:
//                case 6:
//                    findOrderByBuyerId();
//                    break;
                case 7:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
