package ru.astondevs.shop.service;

import ru.astondevs.shop.entity.Order;

import java.util.List;

public interface OrderService {

    Order create();

    Order read();

    boolean update();

    boolean delete();

    List<Order> findAll();

    List<Order> findOrderByBuyerId();
}
