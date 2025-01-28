package dao.impl;

import config.JDBCConnectionConfig;
import dao.BuyerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.ShopDAO;
import entity.Buyer;
import entity.Order;
import entity.Product;
import entity.Shop;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDAOImpl implements OrderDAO {

    private Connection connection = JDBCConnectionConfig.getJDBCConnection();
    private BuyerDAO buyerDAO = new BuyerDAOImpl();
    private ShopDAO shopDAO = new ShopDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

/*    public OrderDAOImpl() {
        connection = JDBCConnectionConfig.getJDBCConnection();
    }*/


    public void createTable() {

        String query = "CREATE TABLE IF NOT EXISTS orders (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "buyer_id BIGINT NOT NULL, " +
                "shop_id BIGINT NOT NULL, " +
                "product_id BIGINT NOT NULL, " +
//                " )";
                "CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id), " +
                "CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shop(id)" +
                "CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
    }

    public void dropTable() {
        String query = "DROP TABLE orders IF EXISTS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Order create(Order order) {

        this.createTable();

        String query = "INSERT INTO orders (buyer_id, shop_id, product_id) VALUES (?,?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getBuyer().getId());
            preparedStatement.setLong(2, order.getShop().getId());
            preparedStatement.setLong(3, order.getProduct().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return order;
    }

    @Override
    public boolean update(Order order) {

        boolean result = false;

        String query = "UPDATE orders SET buyer_id = ?, shop_id = ?, product_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(4, order.getId());
            preparedStatement.setLong(1, order.getBuyer().getId());
            preparedStatement.setLong(2, order.getShop().getId());
            preparedStatement.setLong(3, order.getProduct().getId());

            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public boolean delete(long id) {

        boolean result = false;

        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public Order read(long id) {

        String query = "SELECT orders.buyer_id, orders.shop_id, orders.product_id FROM orders " +
                "WHERE orders.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            ResultSet resultSet = preparedStatement.getResultSet();

//            List<Product> productList = new ArrayList<>();
//            Shop shop =
            Order order = new Order(
                    id,
//                    new Buyer(), new Shop(), new Product());
                    buyerDAO.read(resultSet.getLong("buyer_id")),
                    shopDAO.read(resultSet.getLong("shop_id")),
                    productDAO.read(resultSet.getLong("product_id")));
//                    new ArrayList<Product>());
//            order.setShop(shop);//TODO Как правильно хранить Магазин?
            return order;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return null;//TODO Спросить у Дениса на счёт такого варианта возврата order
    }

    @Override
    public List<Order> findAll() {

        List<Order> orders = new ArrayList<>();
        Order order;
        String query = "SELECT orders.id, orders.buyer_id, orders.shop_id FROM orders";
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            orders = new ArrayList<>();
            while (resultSet.next()) {
                order = new Order(
                        resultSet.getLong("id"),
                        buyerDAO.read(resultSet.getLong("buyer_id")),
                        shopDAO.read(resultSet.getLong("shop_id")),
                        productDAO.read(resultSet.getLong("product_id")));
//                        new ArrayList<Product>());
//                order.setShop(shop);
                orders.add(order);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return orders;
    }

    @Override
    public List<Order> findOrderByBuyerId(long id) {

        String query = "SELECT orders.id, orders.shop_id, orders.product_id WHERE orders.buyer_id = ? ";
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                order = new Order(
                        resultSet.getLong("id"),
                        buyerDAO.read(id),
                        shopDAO.read(resultSet.getLong("shop_id")),
                        productDAO.read(resultSet.getLong("product_id")));
                orderList.add(order);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return orderList;
    }
}
