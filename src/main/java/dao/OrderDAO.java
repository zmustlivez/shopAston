package dao;

import entities.Order;

import java.util.List;

public interface OrderDAO {

    public void createTable();

    public void dropTable();

    long save(Order order);

    boolean update(Order order);

    boolean deleteById(long id);

    Order findById(long id);

    List<Order> findAll();

}
