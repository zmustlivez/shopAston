package ru.astondevs.shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.astondevs.shop.entity.Order;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.repository.BuyerRepository;
import ru.astondevs.shop.repository.OrderRepository;
import ru.astondevs.shop.repository.ProductRepository;
import ru.astondevs.shop.repository.ShopRepository;
import ru.astondevs.shop.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Реализация интерфейса {@link OrderService}, предоставляющая методы для управления заказами.
 * Класс взаимодействует с репозиторием {@link OrderRepository} для выполнения операций с базой данных.
 * Включает методы для создания, поиска, поиска заказа по ID покупателя, обновления, удаления и получения
 * всех заказов.
 *
 * @see OrderService
 * @see OrderRepository
 * @see Service
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Map<String, String> TYPE_NAMES = Map.of(
            "Shop", "магазина",
            "Buyer", "покупателя",
            "Order", "заказа"
    );

    private final Scanner scanner;
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;


    /**
     * Конструктор класса {@link OrderServiceImpl}. Внедряет зависимость {@link OrderRepository}
     * для выполнения операций с базой данных.
     *
     * @param orderRepository репозиторий для работы с заказами.
     */ //TODO Добавить описание для остальных репозиториев, если будут использоваться
    public OrderServiceImpl(Scanner scanner, OrderRepository orderRepository, BuyerRepository buyerRepository, ShopRepository shopRepository, ProductRepository productRepository) {
        this.scanner = scanner;
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    /**
     * Создает новый заказ на основе введенных пользователем данных.
     * Запрашивает id покупателя, id магазина, id продукта после чего сохраняет заказ в базе данных.
     *
     * @return созданный объект {@link Order}.
     */
    @Override
    @Transactional
    public Order create() {
        Order order = new Order();

        long buyerId = inputId("Buyer");
        order.setBuyer(buyerRepository.findById(buyerId).get());

        long shopId = inputId("Shop");
        order.setShop(shopRepository.findById(shopId).get());

        addProduct(order);
        Order tempOrder = orderRepository.save(order);
        return tempOrder;

    }

    /**
     * Находит заказ по его ID и выводит информацию о нем.
     * Если заказ не найден, выводится сообщение об ошибке.
     */
    @Override
    public Order read() {

        long orderId = inputId("Order");
        if (orderId >= 0) {
            Order tempOrder = orderRepository.readOrderById(orderId);
            return tempOrder;
        }
        throw new IllegalArgumentException("Id must be non-negative");
    }

    /**
     * Обновляет данные существующего заказа на основе введенных пользователем данных.
     * Запрашивает новый id покупателя, id магазина, id продукта, после чего обновляет данные в базе данных.
     * Если заказ не найден, выводится сообщение об ошибке.
     */
    @Override
    public boolean update() {
        long id = inputId("Order");

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
            }
//            if (!s.matches(".*\\D.*") || !s.isEmpty()) {
            if (!s.matches("\\d+")) {
                System.out.println("Неверный формат ID. Пожалуйста, введите числовое значение.");
                continue;
            }
            long productId = Long.parseLong(s);
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isEmpty()) {
                System.out.println("Продукт с таким ID не найден.");
                continue;
            }
            Product product = optionalProduct.get();

//            Product product = productRepository.findById(productId).get();
//            product.setOrder(new ArrayList<>(List.of(order)));
            product.getOrder().add(order);

            order.addProduct(product);

/*            System.out.println("Неверный формат ID. Пожалуйста, введите числовое значение.");
            Product product = productRepository.findById(productId).get();
            product.setOrder(new ArrayList<>(List.of(order)));
            order.addProduct(product);*/
                    /*order.addProduct(productRepository.findById(productId).orElseThrow(() ->
                            new IllegalArgumentException("Продукт с ID " + productId + " не найден")));*/

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
    @Transactional
    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
//        orders.iterator().forEachRemaining(order -> order.setProducts(List.of(productRepository.findById(order.getProducts().get(0).getId()).orElseThrow(() ->new RuntimeException()))));
        System.out.println(orders);
        return orders;
    }

    @Override
    public List<Order> findOrderByBuyerId() {

        long buyerId = inputId("Buyer");

        List<Order> orders = orderRepository.findOrdersByBuyer(buyerId);
        return orders;
    }

    private long inputId(String type) {
        long id;
        while (true) {
            String typeName = TYPE_NAMES.getOrDefault(type, type);
            System.out.printf("Введите ID %s:%n", typeName);

            if (!scanner.hasNextLong()) {
                System.out.println("Ошибка! Введите корректное число.");
                scanner.nextLine();
                continue;
            }

            id = scanner.nextLong();
            scanner.nextLine();

            if (id > 0) {
                return id;
            }

            System.out.println("Ошибка! ID должен быть положительным числом.");
        }
    }

}
