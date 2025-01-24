package dao;

import entities.Order;
import entities.Product;

import java.util.List;

public interface OrderDAO {

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(int id);

    Order findOrderById(int id);

    List<Order> findAllOrders();

}
