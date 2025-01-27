import scanner.BuyerMenu;
import scanner.ShopMenu;

import java.util.Scanner;

public class shopApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("Выберите сущность:");
            System.out.println("1. Покупатель");
            System.out.println("4. Магазин");
            System.out.println("5. Выход");
            int entityChoice = scanner.nextInt();
            scanner.nextLine();

            switch (entityChoice) {
                case 1:
                    BuyerMenu.Menu();
                    break;
                case 4:
                    ShopMenu.Menu();
                case 5:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
