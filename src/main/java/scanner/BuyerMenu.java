package scanner;

import scanner.service.BuyerServiceMenu;

import java.util.Scanner;

public class BuyerMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void BuyerMenu() {
        while (true) {
            System.out.println("Выберите действие для Покупателя:");
            System.out.println("1. Создать покупателя");
//            System.out.println("2. Найти покупателя по ID");
//            System.out.println("3. Обновить покупателя");
//            System.out.println("4. Удалить покупателя");
//            System.out.println("5. Найти всех покупателей");
//            System.out.println("6. Найти заказы покупателя");
            System.out.println("7. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    BuyerServiceMenu.createBuyer();
                    break;
//                case 2:
//                    readBuyer();
//                    break;
//                case 3:
//                    updateBuyer();
//                    break;
//                case 4:
//                    deleteBuyer();
//                    break;
//                case 5:
//                    findAllBuyers();
//                    break;
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
