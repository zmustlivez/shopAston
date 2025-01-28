package service;

import dao.BuyerDAO;
import dao.impl.BuyerDAOImpl;
import entity.Buyer;

import java.util.Scanner;

public class
BuyerServiceMenu {

    static Scanner scanner = new Scanner(System.in);

    static BuyerDAO buyerDAO = new BuyerDAOImpl();

    public static void create() {
        System.out.println("Введите имя покупателя:");
        String name = scanner.nextLine();

        long cardNumber;
        while (true) {
            System.out.println("Введите номер карты покупателя (4 цифры):");
            if (scanner.hasNextLong()) {
                cardNumber = scanner.nextLong();
                if (String.valueOf(cardNumber).length() == 4) {
                    break;
                } else {
                    System.out.println("Номер карты должен состоять из 4 цифр. Пожалуйста, попробуйте снова.");
                }
            } else {
                System.out.println("Некорректный ввод. Номер карты должен быть числом (4 цифры)");
                scanner.next();
            }
        }
        scanner.nextLine();

        long saleValue;
        while (true) {
            System.out.println("Введите сумму скидки для покупателя (от 0 до 100):");
            if (scanner.hasNextLong()) {
                saleValue = scanner.nextLong();
                if (saleValue >= 0 && saleValue <= 100) {
                    break;
                } else {
                    System.out.println("Сумма скидки должна быть от 0 до 100. Пожалуйста, попробуйте снова.");
                }
            } else {
                System.out.println("Некорректный ввод. Сумма скидки должна быть числом от 0 до 100");
                scanner.next();
            }
        }
        scanner.nextLine();

        Buyer buyer = new Buyer();
        buyer.setName(name);
        buyer.setCardNumber(cardNumber);
        buyer.setSaleValue(saleValue);

        buyer = buyerDAO.create(buyer);
        System.out.println("Покупатель создан с ID: " + buyer.getId());
    }

    public static void read() {
        System.out.println("Введите ID покупателя:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Buyer buyer = buyerDAO.read(id);
        if (buyer != null) {
            System.out.println("Найден покупатель: " + buyer);
        } else {
            System.out.println("Покупатель с ID " + id + " не найден");
        }
    }

    public static void update() {
        System.out.println("Введите ID покупателя для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        Buyer buyer = buyerDAO.read(id);
        if (buyer == null) {
            System.out.println("Покупатель с ID " + id + " не найден");
            return;
        }

        System.out.println("Текущее имя покупателя: " + buyer.getName());
        System.out.println("Введите новое имя покупателя:");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            buyer.setName(name);
        }

        System.out.println("Текущий номер карты покупателя: " + buyer.getCardNumber());
        System.out.println("Введите новый номер карты покупателя:");
        String cardNumberInput = scanner.nextLine();
        if (!cardNumberInput.isEmpty()) {
            try {
                long cardNumber = Long.parseLong(cardNumberInput);
                buyer.setCardNumber(cardNumber);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат номера карты. Номер карты не изменен(");
            }
        }

        System.out.println("Текущая сумма скидки покупателя: " + buyer.getSaleValue());
        System.out.println("Введите новую сумму скидки:");
        String saleValueInput = scanner.nextLine();
        if (!saleValueInput.isEmpty()) {
            try {
                long saleValue = Long.parseLong(saleValueInput);
                buyer.setSaleValue(saleValue);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат суммы скидки. Сумма скидки не изменена(");
            }
        }
        buyerDAO.update(buyer);
        System.out.println("Покупатель обновлен: " + buyer);
    }

    public static void delete() {
        System.out.println("Введите ID покупателя для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        buyerDAO.delete(id);
        System.out.println("Покупатель с ID " + id + " удален");
    }

    public static void findAll() {
        System.out.println("Список всех покупателей:");
        buyerDAO.findAll().forEach(System.out::println);
    }

}
