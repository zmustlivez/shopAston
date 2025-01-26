/*
package services;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.ShopDAO;
import entity.Order;
import entity.Product;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final ShopDAO shopDAO;

    public OrderServiceImpl(OrderDAO orderDAO, ProductDAO productDAO, ShopDAO shopDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.shopDAO = shopDAO;
    }

    @Override
    public long create(Order order) {
        return orderDAO.create(order);
    }

    @Override
    public boolean update(Order order) {
        return orderDAO.update(order);
    }

    @Override
    public boolean delete(long id) {
        return orderDAO.delete(id);
    }

    @Override
    public Order read(long id) {
        return orderDAO.read(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public List<Product> findAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Order findByBuyerId(long id){
        return orderDAO.findByBuyerId(id);
    }
}
*/
