package ru.astondevs.shop.service.impl;

import org.springframework.stereotype.Service;
import ru.astondevs.shop.entity.Order;
import ru.astondevs.shop.repository.BuyerRepository;
import ru.astondevs.shop.repository.OrderRepository;
import ru.astondevs.shop.repository.ProductRepository;
import ru.astondevs.shop.repository.ShopRepository;
import ru.astondevs.shop.service.BuyerServiceMenu;
import ru.astondevs.shop.service.OrderService;

import java.util.List;
import java.util.Scanner;

@Service
public class OrderServiceImpl implements OrderService {
    private final Scanner scanner;
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(Scanner scanner, OrderRepository orderRepository, BuyerRepository buyerRepository, ShopRepository shopRepository, ProductRepository productRepository) {
        this.scanner = scanner;
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create() {
        Order order = new Order();

        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();
        order.setBuyer(buyerRepository.findById(buyerId).get());

        System.out.println("Введите ID магазина");
        long shopId = scanner.nextLong();
        scanner.nextLine();
        order.setShop(shopRepository.findById(shopId).get());

        addProduct(order);
        Order tempOrder = orderRepository.save(order);
        return tempOrder;

    }

    @Override
    public Order read() {
        System.out.println("Введите ID заказа");
        long id = scanner.nextLong();
        scanner.nextLine();
        if (id >= 0) {
            Order tempOrder = orderRepository.readOrderById(id);
            return tempOrder;
        }
        throw new IllegalArgumentException("Id must be non-negative");
    }


    @Override
    public boolean update() {
        System.out.println("Введите ID заказа для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        if (!orderRepository.existsById(id)) {
            System.out.println("Заказ с ID " + id + " не найден");
            return false;
        }
        Order order = orderRepository.readOrderById(id);
        System.out.printf("Текущий ID покупателя: %d \n" +
                        "Текущее имя покупателя %s", order.getBuyer().getId(),
                order.getShop().getName());
        System.out.println("Введите новый ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();
        order.getBuyer().setId(buyerId);

        System.out.printf("Текущий ID магазина: %d \n" +
                        "Текущее имя магазина %s", order.getShop().getId(),
                order.getShop().getName());
        System.out.println("Введите новый ID магазина:");
        long shopId = scanner.nextLong();
        scanner.nextLine();
        order.getShop().setId(shopId);

        addProduct(order);

        orderRepository.save(order);
        return true;

    }

    private void addProduct(Order order) {
        while (true) {
            System.out.println("Введите ID продукта или напишите stop");
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("stop")) {
                break;
            } else {
                try {
                    long productId = Long.parseLong(s);
                    order.addProduct(productRepository.findById(productId).orElseThrow(() ->
                            new IllegalArgumentException("Продукт с ID " + productId + " не найден")));
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ID. Пожалуйста, введите числовое значение.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean delete() {

        System.out.println("Введите ID заказа для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Заказ с ID " + id + " не найден");
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public List<Order> findOrderByBuyerId() {

        System.out.println("Введите ID покупателя:");
        long buyerId = scanner.nextLong();
        scanner.nextLine();

        List<Order> orders = orderRepository.findOrdersByBuyer(buyerId);
        return orders;
    }
}
