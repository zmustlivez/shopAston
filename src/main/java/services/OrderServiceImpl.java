package services;

import dao.OrderDAO;
import dao.ProductDAO;
import entities.Order;
import entities.Product;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    public OrderServiceImpl(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @Override
    public long saveOrder(Order order) {
        return orderDAO.saveOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderDAO.updateOrder(order);
    }

    @Override
    public boolean deleteOrder(int id) {
        return orderDAO.deleteOrder(id);
    }

    @Override
    public Order findOrderById(int id) {
        return orderDAO.findOrderById(id);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDAO.findAllOrders();
    }

    @Override
    public List<Product> findAllProducts() {
        return productDAO.getAllProducts();
    }
}
