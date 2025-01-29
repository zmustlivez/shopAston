import controller.BuyerMenu;
import controller.OrderMenu;
import controller.ProductMenu;
import controller.ShopMenu;
import entity.Order;
import service.OrderServiceMenu;

import java.util.Scanner;

public class shopApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final OrderMenu orderMenu = new OrderMenu();
        final ProductMenu productMenu = new ProductMenu();
        while (true) {
            System.out.println("Выберите сущность:");
            System.out.println("1. Покупатель");
            System.out.println("2. Заказ");
            System.out.println("3. Продукт");
            System.out.println("4. Магазин");
            System.out.println("5. Выход");
            int entityChoice = scanner.nextInt();
            scanner.nextLine();

            switch (entityChoice) {
                case 1:
                    BuyerMenu.menu();
                    break;
                case 2:
                    orderMenu.menu();
                    break;
                case 3:
                    productMenu.menu();
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
