package ru.astondevs.shop.service.impl;

import ru.astondevs.shop.entity.Buyer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.astondevs.shop.repository.BuyerRepository;

import ru.astondevs.shop.service.BuyerService;

import java.util.List;
import java.util.Scanner;

/**
 * Реализация интерфейса {@link BuyerService}, предоставляющая методы для управления покупателями.
 * Класс взаимодействует с репозиторием {@link BuyerRepository} для выполнения операций с базой данных.
 * Включает методы для создания, поиска, обновления, удаления и получения всех покупателей.
 *
 * @see BuyerService
 * @see BuyerRepository
 * @see Service
 * @see Slf4j
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    private final Scanner scanner = new Scanner(System.in);

    private final BuyerRepository buyerRepository;

    /**
     * Конструктор класса {@link BuyerServiceImpl}. Внедряет зависимость {@link BuyerRepository}
     * для выполнения операций с базой данных.
     *
     * @param buyerRepository репозиторий для работы с покупателями.
     */
    public BuyerServiceImpl(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    /**
     * Создает нового покупателя на основе введенных пользователем данных.
     * Запрашивает имя, номер карты и сумму скидки, после чего сохраняет покупателя в базе данных.
     *
     * @return созданный объект {@link Buyer}.
     */
    @Override
    public Buyer create() {

        String name = setName();
        long cardNumber = setCardNumber();
        long saleValue = setSaleValue();

        Buyer buyer = new Buyer();
        buyer.setName(name);
        buyer.setCardNumber(cardNumber);
        buyer.setSaleValue(saleValue);

        Buyer savedBuyer = buyerRepository.save(buyer);
        log.info("Successfully created buyer with id: {}", buyer.getId());
        return savedBuyer;
    }

    /**
     * Находит покупателя по его ID и выводит информацию о нем.
     * Если покупатель не найден, выводится сообщение об ошибке.
     */
    @Override
    public void read() {
        System.out.println("Введите ID покупателя:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Buyer buyer = buyerRepository.findById(id).orElse(null);
        if (buyer != null) {
            System.out.println("Найден покупатель: " + buyer);
        } else {
            log.error("Buyer to read with id: {} not found", id);
        }
    }

    /**
     * Обновляет данные существующего покупателя на основе введенных пользователем данных.
     * Запрашивает новое имя, номер карты и сумму скидки, после чего обновляет данные в базе данных.
     * Если покупатель не найден, выводится сообщение об ошибке.
     */
    @Override
    public void update() {
        System.out.println("Введите ID покупателя для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Buyer buyer = buyerRepository.findById(id).orElse(null);
        if (buyer == null) {
            log.error("Buyer to update with id: {} not found", id);
            return;
        }

        System.out.println("Текущее имя покупателя: " + buyer.getName());
        String name = setName();

        System.out.println("Текущий номер карты покупателя: " + buyer.getCardNumber());
        long cardNumber = setCardNumber();

        System.out.println("Текущая сумма скидки покупателя: " + buyer.getSaleValue());
        long saleValue = setSaleValue();

        buyer.setName(name);
        buyer.setCardNumber(cardNumber);
        buyer.setSaleValue(saleValue);

        buyerRepository.save(buyer);
        log.info("Successfully updated buyer with id: {}", buyer.getId());
    }

    /**
     * Запрашивает у пользователя ввод имени покупателя и проверяет, что оно не пустое.
     * Используется в методах:
     * <ul>
     *     <li>{@link #create()} - для создания нового покупателя.</li>
     *     <li>{@link #update()} - для обновления существующего покупателя.</li>
     * </ul>
     *
     * @return имя покупателя.
     */
    private String setName() {
        String name;
        while (true) {
            System.out.println("Введите имя покупателя:");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                break;
            } else {
                System.out.println("Имя не должно быть пустым. Пожалуйста, попробуйте снова.");
            }
        }
        return name;
    }

    /**
     * Запрашивает у пользователя ввод номера карты покупателя и проверяет, что он состоит из 4 цифр.
     * Используется в методах:
     * <ul>
     *     <li>{@link #create()} - для создания нового покупателя.</li>
     *     <li>{@link #update()} - для обновления существующего покупателя.</li>
     * </ul>
     *
     * @return номер карты покупателя.
     */
    private long setCardNumber() {
        long cardNumber;
        while (true) {
            System.out.println("Введите номер карты покупателя (4 цифры):");
            String input = scanner.nextLine().trim();

            try {
                cardNumber = Long.parseLong(input);
                if (input.length() == 4) {
                    break;
                } else {
                    System.out.println("Номер карты должен состоять из 4 цифр. Пожалуйста, попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Номер карты должен быть числом (4 цифры).");
            }
        }
        return cardNumber;
    }

    /**
     * Запрашивает у пользователя ввод суммы скидки и проверяет, что она находится в диапазоне от 0 до 100.
     * Используется в методах:
     * <ul>
     *     <li>{@link #create()} - для создания нового покупателя.</li>
     *     <li>{@link #update()} - для обновления существующего покупателя.</li>
     * </ul>
     *
     * @return сумма скидки.
     */
    private long setSaleValue() {
        long saleValue;
        while (true) {
            System.out.println("Введите сумму скидки для покупателя (от 0 до 100):");
            String input = scanner.nextLine().trim();

            try {
                saleValue = Long.parseLong(input);
                if (saleValue >= 0 && saleValue <= 100) {
                    break;
                } else {
                    System.out.println("Сумма скидки должна быть от 0 до 100. Пожалуйста, попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Сумма скидки должна быть числом от 0 до 100.");
            }
        }
        return saleValue;
    }

    /**
     * Удаляет покупателя по его ID.
     * Если покупатель не найден, выводится сообщение об ошибке.
     */
    @Override
    public void delete() {
        System.out.println("Введите ID покупателя для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        if (buyerRepository.existsById(id)) {
            log.info("Buyer was deleted successfully with id: {}", id);
            buyerRepository.deleteById(id);
        } else {
            log.error("Buyer to delete with id: {} not found", id);
        }
    }

    /**
     * Выводит список всех покупателей.
     * Если список пуст, выводится соответствующее сообщение.
     */
    @Override
    public void findAll() {
        System.out.println("Список всех покупателей:");
        List<Buyer> all = buyerRepository.findAll();

        if (!all.isEmpty()) {
            all.forEach(System.out::println);
        } else {
            System.out.println("Список покупателей пуст");
        }
    }
}
