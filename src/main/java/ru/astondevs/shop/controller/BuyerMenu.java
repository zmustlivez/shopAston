package ru.astondevs.shop.controller;

import org.springframework.stereotype.Component;

import ru.astondevs.shop.service.BuyerService;

import java.util.Scanner;

/**
 * Класс {@link BuyerMenu} представляет собой компонент Spring, отвечающий за взаимодействие с пользователем
 * через консольное меню для управления покупателями. Он предоставляет возможность создания, поиска,
 * обновления, удаления и отображения всех покупателей.
 *
 * @see Component
 */
@Component
public class BuyerMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final BuyerService buyerService;

    /**
     * Конструктор класса. Внедряет зависимость `BuyerService` для выполнения операций
     * с покупателями.
     *
     * @param buyerService сервис для работы с покупателями.
     */
    public BuyerMenu(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    /**
     * Отображает меню для работы с покупателями и обрабатывает выбор пользователя.
     * Пользователь может выбрать одно из следующих действий:
     * <ul>
     *     <li>1. Создать покупателя</li>
     *     <li>2. Найти покупателя по ID</li>
     *     <li>3. Обновить покупателя</li>
     *     <li>4. Удалить покупателя</li>
     *     <li>5. Найти всех покупателей</li>
     *     <li>6. Вернуться в главное меню</li>
     * </ul>
     * Метод выполняется в бесконечном цикле до тех пор, пока пользователь не выберет опцию
     * "Вернуться в главное меню".
     */
    public void menu() {
        while (true) {
            System.out.println("Выберите действие для Покупателя:");
            System.out.println("1. Создать покупателя");
            System.out.println("2. Найти покупателя по ID");
            System.out.println("3. Обновить покупателя");
            System.out.println("4. Удалить покупателя");
            System.out.println("5. Найти всех покупателей");
            System.out.println("6. Вернуться в главное меню");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    buyerService.create();
                    break;
                case 2:
                    buyerService.read();
                    break;
                case 3:
                    buyerService.update();
                    break;
                case 4:
                    buyerService.delete();
                    break;
                case 5:
                    buyerService.findAll();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
