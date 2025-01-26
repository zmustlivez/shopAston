package dao;

import config.Config;
import entities.Order;
import entities.Shop;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDAOImpl implements OrderDAO {

    private final Connection connection = Config.getJDBCConnection();

    @Override
    public void createOrderTable() {
        String query = "CREATE TABLE IF NOT EXIST orders (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "price BIGINT NOT NULL," +
                "product_id BIGINT NOT NULL," +
                "CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shop(id)" +
                " ,)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void dropOrderTable() {
        String query = "DROP TABLE orders IF EXISTS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public long saveOrder(Order order) {
        long id = -1;
        this.createOrderTable();
        String query = "INSERT INTO orders VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getPrice());
            preparedStatement.setLong(2, order.getShop().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            connection.commit();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Connection failed");
                log.error(e.getMessage(), e);
            }
        }
        return id;
    }

    @Override
    public boolean updateOrder(Order order) {
        boolean result = false;
        String query = "UPDATE orders SET id = ?, price = ?, shop_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setLong(2, order.getPrice());
            preparedStatement.setLong(3, order.getShop().getId());
            result = preparedStatement.executeUpdate() > 0 ? true : false;
            connection.commit();
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
    public boolean deleteOrder(int id) {
        boolean result = false;
        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0 ? true : false;
            connection.commit();
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
    public Order findOrderById(int id) {
        Order order = null;
        Shop shop = null;
        String query = "SELECT * FROM orders JOIN shops ON orders.shop_id WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            order = new Order();
            while (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setPrice(resultSet.getLong("price"));
                shop.setId(resultSet.getLong("shop_id"));
                shop.setName(resultSet.getString("shop_name"));
                order.setShop(shop);
            }
            connection.commit();
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
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        Order order;
        Shop shop = null;
        String query = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            ResultSet resultSet = statement.getResultSet();
            orders = new ArrayList<>();
            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setPrice(resultSet.getLong("price"));
                shop.setId(resultSet.getLong("shop_id"));
                shop.setName(resultSet.getString("shop_name"));
                order.setShop(shop);
                orders.add(order);
            }
            connection.commit();
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
}
