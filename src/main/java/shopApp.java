import scanner.BuyerMenu;

import java.util.Scanner;

public class shopApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("Выберите сущность:");
            System.out.println("1. Покупатель");
            System.out.println("2. Выход");
            int entityChoice = scanner.nextInt();
            scanner.nextLine();

            switch (entityChoice) {
                case 1:
                    BuyerMenu.BuyerMenu();
                    break;
                case 2:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
