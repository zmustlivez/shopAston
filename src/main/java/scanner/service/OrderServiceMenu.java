package scanner.service;

import dao.OrderDAO;
import dao.impl.OrderDAOImpl;
import entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OrderServiceMenu {

    static Scanner scanner = new Scanner(System.in);

    static OrderDAO orderDAO = new OrderDAOImpl();

    public static void createOrder() {
        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Введите сумму заказа:");
        long price = scanner.nextLong();
        scanner.nextLine();

        Order order = new Order();
        order.setPrice(price);
        order.setBuyerId(buyerId);

        order = orderDAO.createOrder(order);
        System.out.println("Заказ создан с ID: " + order.getId());
    }

    public static void findOrderByBuyerId() {
        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();

        List<Order> orders = orderDAO.findOrderByBuyerId(buyerId);
        if (!orders.isEmpty()) {
            System.out.println("Найдены заказы:");
            orders.forEach(System.out::println);
        } else {
            System.out.println("Заказы для покупателя с ID " + buyerId + " не найдены");
        }
    }

}
