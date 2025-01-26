package scanner.service;

import dao.BuyerDAO;
import dao.impl.BuyerDAOImpl;
import entity.Buyer;

import java.util.Scanner;

public class BuyerServiceMenu {

    static Scanner scanner = new Scanner(System.in);

    static BuyerDAO buyerDAO = new BuyerDAOImpl();

    public static void createBuyer() {
        System.out.println("Введите имя покупателя:");
        String name = scanner.nextLine();
        System.out.println("Введите номер карты покупателя:");
        long cardNumber = scanner.nextLong();
        System.out.println("Введите сумму скидки для покупателя:");
        long saleValue = scanner.nextLong();
        scanner.nextLine();

        Buyer buyer = new Buyer();
        buyer.setName(name);
        buyer.setCardNumber(cardNumber);
        buyer.setSaleValue(saleValue);

        buyer = buyerDAO.createBuyer(buyer);
        System.out.println("Покупатель создан с ID: " + buyer.getId());
    }

}
