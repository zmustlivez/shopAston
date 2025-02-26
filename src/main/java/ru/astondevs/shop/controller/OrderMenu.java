package ru.astondevs.shop.controller;

import org.springframework.stereotype.Component;
import ru.astondevs.shop.service.OrderService;

import java.util.Scanner;

/**
 * Класс {@link OrderMenu} представляет собой компонент Spring, отвечающий за взаимодействие с пользователем
 * через консольное меню для управления заказами. Он предоставляет возможность создания, поиска,
 * обновления, удаления и отображения заказов.
 *
 * @see Component
 */
@Component
public class OrderMenu {

    private static final Scanner scanner = new Scanner(System.in);

    private final OrderService orderService;

    /**
     * Конструктор класса. Внедряет зависимость {@link OrderService} для выполнения операций с заказами.
     *
     * @param orderService сервис для работы с заказами.
     */
    public OrderMenu(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Отображает меню для работы с заказами и обрабатывает выбор пользователя.
     * Пользователь может выбрать одно из следующих действий:
     * <ul>
     *     <li>1. Создать заказ</li>
     *     <li>2. Внести изменения в заказ</li>
     *     <li>3. Удалить заказ</li>
     *     <li>4. Найти заказ по ID</li>
     *     <li>5. Получить список всех заказов</li>
     *     <li>6. Найти заказы по ID покупателя</li>
     *     <li>7. Вернуться в главное меню</li>
     * </ul>
     * Метод выполняется в бесконечном цикле до тех пор, пока пользователь не выберет опцию
     * "Вернуться в главное меню".
     */
    public void menu() {
        while (true) {
            System.out.printf("Введите цифру означающую действие\n" +
                    "1 - Записать заказ в таблицу с заказами (Создать заказ)\n" +
                    "2 - Внести изменения в заказ (потребуется id заказа)\n" +
                    "3 - Удалить заказ (потребуется id заказа)\n" +
                    "4 - Найти заказ (потребуется id заказа)\n" +
                    "5 - Получить список всех заказов из таблицы\n" +
                    "6 - Найти заказ по id покупателя\n" +
                    "7 - Вернуться в главное меню\n");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (actionChoice) {
                case 1:
                    orderService.create();
                    break;
                case 2:
                    orderService.update();
                    break;
                case 3:
                    orderService.delete();
                    break;
                case 4:
                    orderService.read();
                    break;
                case 5:
                    orderService.findAll();
                    break;
                case 6:
                    orderService.findOrderByBuyerId();
                    break;
                case 7:
                    return; // Возврат в главное меню
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

}
