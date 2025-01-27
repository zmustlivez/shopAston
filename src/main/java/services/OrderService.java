package services;

import entities.Order;
import entities.Product;

import java.util.List;

public interface OrderService {

    public void createOrderTable();

    public void dropOrderTable();

    long saveOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(int id);

    Order findOrderById(int id);

    List<Order> findAllOrders();

    List<Product> findAllProducts();
}
