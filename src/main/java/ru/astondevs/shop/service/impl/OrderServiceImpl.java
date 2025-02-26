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
     * Конструктор класса {@link OrderServiceImpl}. Внедряет зависимости, необходимые для выполнения
     * операций с заказами, покупателями, магазинами и товарами.
     *
     * @param scanner         объект {@link Scanner} для ввода данных с консоли.
     * @param orderRepository репозиторий {@link OrderRepository} для работы с заказами.
     * @param buyerRepository репозиторий {@link BuyerRepository} для работы с покупателями.
     * @param shopRepository  репозиторий {@link ShopRepository} для работы с магазинами.
     * @param productRepository репозиторий {@link ProductRepository} для работы с продуктами.
     */
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
    @Transactional
    public Order read() {

        long orderId = inputId("Order");
        if (orderId >= 0) {
            Order tempOrder = orderRepository.readOrderById(orderId);
            System.out.println(tempOrder);
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
    @Transactional
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

        long buyerId = inputId("Buyer");
        order.getBuyer().setId(buyerId);

        System.out.printf("Текущий ID магазина: %d \n" +
                        "Текущее имя магазина %s", order.getShop().getId(),
                order.getShop().getName());

        long shopId = inputId("Shop");
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

            product.getOrder().add(order);

            order.addProduct(product);
        }
    }

    /**
     * Удаляет заказ по его ID.
     * Если покупатель не найден, выводится сообщение об ошибке.
     */
    @Override
    @Transactional
    public boolean delete() {

        long id = inputId("Order");

        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Заказ с ID " + id + " не найден");
        }
        orderRepository.deleteById(id);
        return true;
    }

    /**
     * Выводит и возвращает список всех заказов.
     * Если список пуст, выводится соответствующее сообщение.
     */
    @Override
    @Transactional
    public List<Order> findAll() {

        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            orders.forEach(System.out::println);
        } else {
            System.out.println("Список покупателей пуст");
        }

        return orders;
    }

    /**
     * Производит поиск всех заказов по определенному покупателю и возвращает его.
     */
    @Override
    public List<Order> findOrderByBuyerId() {

        long buyerId = inputId("Buyer");

        List<Order> orders = orderRepository.findOrdersByBuyer(buyerId);
        return orders;
    }

    /**
     * Запрашивает у пользователя ввод id покупателя, магазина и заказа, проверяя что он ввод не пустой, положительный
     * и содержит только числовые значения.
     * Используется в методах:
     * <ul>
     *     <li>{@link #create()} - для создания нового заказа.</li>
     *     <li>{@link #update()} - для обновления существующего заказа.</li>
     *     <li>{@link #delete()} - для удаления существующего заказа.</li>
     *     <li>{@link #findOrderByBuyerId()} - для поиска по покупателю.</li>
     * </ul>
     *
     * @return значение.
     */
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
