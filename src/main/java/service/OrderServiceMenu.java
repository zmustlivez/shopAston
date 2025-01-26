package service;

import dao.OrderDAO;
import dao.impl.OrderDAOImpl;
import entity.Buyer;
import entity.Order;

import java.util.List;
import java.util.Scanner;

public class OrderServiceMenu {

    static Scanner scanner = new Scanner(System.in);

    static OrderDAO orderDAO = new OrderDAOImpl();

    public static void create() {
        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();

/*        System.out.println("Введите сумму заказа:");
        long price = scanner.nextLong();
        scanner.nextLine();*/

        System.out.println("Введите ID магазина");
        long shopId = scanner.nextLong();
        scanner.nextLine();

        Order order = new Order();
        order.getBuyer().setId(buyerId);
        order.getShop().setId(shopId);

        order = orderDAO.create(order);
        System.out.println("Заказ создан с ID: " + order.getId());
    }

    public static void read() {
        System.out.println("Введите ID заказа");
        long id = scanner.nextLong();
        scanner.nextLine();
        Order order = orderDAO.read(id);
        if (order != null) {
            System.out.println("Найден заказ: " + order);
        } else {
            System.out.println("Заказ с ID " + id + " не найден");

        }
    }

    public static void update() {
        System.out.println("Введите ID заказа для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Order order = orderDAO.read(id);
        if (order == null) {
            System.out.println("Заказ с ID " + id + " не найден");
            return;
        }

        System.out.printf("Текущий ID покупателя: %d \n" +
                        "Текущее имя покупателя %s", order.getBuyer().getId(),
                BuyerServiceMenu.buyerDAO.read(order.getBuyer().getId()).getName());
        System.out.println("Введите новый ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();
        order.getBuyer().setId(buyerId);

        System.out.printf("Текущий ID магазина: %d \n" +
                        "Текущее имя магазина %s", order.getShop().getId(),
                ShopServiceMenu.shopDAO.read(order.getShop().getId()).getName());
        System.out.println("Введите новый ID магазина:");
        long shopId = scanner.nextLong();
        scanner.nextLine();
        order.getShop().setId(shopId);

        System.out.printf("Текущий ID продукта: %d \n" +
                        "Текущее имя продукта %s", order.getProduct().getId(),
                ProductServiceMenu.productDAO.read(order.getProduct().getId()).getName());
        System.out.println("Введите новый ID продукта:");
        long productId = scanner.nextLong();
        scanner.nextLine();
        order.getProduct().setId(productId);

        orderDAO.update(order);
    }

    public static void delete() {
        System.out.println("Введите ID заказа для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        orderDAO.delete(id);
        System.out.println("Заказ с ID " + id + " удален");
    }

    public static void findAll() {
        System.out.println("Список всех заказов:");
        orderDAO.findAll().forEach(System.out::println);
    }

    public static void findOrderByBuyerId() {
        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();

        List<Order> orders = orderDAO.findByBuyerId(buyerId);
        if (!orders.isEmpty()) {
            System.out.println("Найдены заказы:");
            orders.forEach(System.out::println);
        } else {
            System.out.println("Заказы для покупателя с ID " + buyerId + " не найдены");
        }
    }

}
