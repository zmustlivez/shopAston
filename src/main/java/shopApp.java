import controller.BuyerMenu;
import controller.OrderMenu;
import controller.ProductMenu;

import java.util.Scanner;

public class shopApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("Выберите сущность:");
            System.out.println("1. Заказ");
            System.out.println("2. Покупатель");
            System.out.println("3. Продукт");
            System.out.println("4. Выход");
            int entityChoice = scanner.nextInt();
            scanner.nextLine();

            switch (entityChoice) {
                case 1:
                    OrderMenu.menu();
                    break;
                case 2:
                    BuyerMenu.menu();
                    break;
                case 3:
                    new ProductMenu().menu();
                    break;
                case 4:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
