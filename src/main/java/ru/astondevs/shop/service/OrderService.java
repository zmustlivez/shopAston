package ru.astondevs.shop.service;

import ru.astondevs.shop.entity.Order;

import java.util.List;

/**
 * Интерфейс {@link OrderService} определяет контракт для сервиса, отвечающего за управление заказами.
 * Он предоставляет методы для создания, поиска, поиска заказа по ID покупателя, обновления, удаления и получения
 * всех заказов.
 */
public interface OrderService {

    Order create();

    Order read();

    boolean update();

    boolean delete();

    List<Order> findAll();

    List<Order> findOrderByBuyerId();
}
