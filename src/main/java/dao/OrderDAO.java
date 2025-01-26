package dao;

import entities.Order;

import java.util.List;

public interface OrderDAO {

    public void createOrderTable();

    public void dropOrderTable();

    long saveOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(int id);

    Order findOrderById(int id);

    List<Order> findAllOrders();

}
