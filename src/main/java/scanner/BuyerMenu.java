package scanner;

import scanner.service.BuyerServiceMenu;

import java.util.Scanner;

public class BuyerMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void Menu() {
        while (true) {
            System.out.println("Выберите действие для Покупателя:");
            System.out.println("1. Создать покупателя");
            System.out.println("2. Найти покупателя по ID");
            System.out.println("3. Обновить покупателя");
            System.out.println("4. Удалить покупателя");
            System.out.println("5. Найти всех покупателей");
            System.out.println("6. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    BuyerServiceMenu.createBuyer();
                    break;
                case 2:
                    BuyerServiceMenu.readBuyer();
                    break;
                case 3:
                    BuyerServiceMenu.updateBuyer();
                    break;
                case 4:
                    BuyerServiceMenu.deleteBuyer();
                    break;
                case 5:
                    BuyerServiceMenu.findAllBuyers();
                    break;
                case 6:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
