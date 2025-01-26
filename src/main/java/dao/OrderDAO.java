package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO {

    Order create(Order order);

    Order read(long id);

    boolean update(Order order);

    boolean delete(long id);

    List<Order> findAll();

    Order findOrderByBuyerId(long id);

}
