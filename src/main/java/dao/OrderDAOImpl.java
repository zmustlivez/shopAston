package dao;

import config.Config;
import entities.Buyer;
import entities.Order;
import entities.Product;
import entities.Shop;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDAOImpl implements OrderDAO {

    private Connection connection = Config.getJDBCConnection();
    private BuyerDAO buyerDAO = new BuyerDAOImpl();
    private ShopDAO shopDAO = new ShopDAOImpl();

/*    public OrderDAOImpl() {
        connection = Config.getJDBCConnection();
    }*/

    @Override
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS orders (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "buyer_id BIGINT NOT NULL, " +
                "shop_id BIGINT NOT NULL, " +
//               " )";
                "CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id), " +
                "CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shop(id)" +
                ")";

/*
        String query = "CREATE TABLE IF NOT EXISTS orders (" +
                "id BIGSERIAL PRIMARY KEY,  " +
                " buyer_id BIGINT NOT NULL," +
                "    shop_id BIGINT NOT NULL," +
                "CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id)" +
                "CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shop(id)" +
                ")";
*/
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
    }

    @Override
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
    public long save(Order order) {
        long id = -1;
        this.createTable();
        String query = "INSERT INTO orders VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getBuyer().getId());
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
    public boolean update(Order order) {
        boolean result = false;
        String query = "UPDATE orders SET buyer_id = ?, shop_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(3, order.getId());
            preparedStatement.setLong(1, order.getBuyer().getId());
            preparedStatement.setLong(2, order.getShop().getId());
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
    public boolean deleteById(long id) {
        boolean result = false;
        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
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
    public Order findById(long id) {

        String query = "SELECT orders.buyer_id, orders.shop_id FROM orders " +
//                "JOIN shops ON orders.shop_id = shops.id " +
//                "join order_products ON order_products.order_id = order.id " +
                "WHERE order.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

//            List<Product> productList = new ArrayList<>();
//            Shop shop =
            Order order = new Order(
                    id,
                    buyerDAO.findById(resultSet.getLong("buyer_id")),
                    shopDAO.getById(resultSet.getLong("shop_id")),
                    new ArrayList<Product>());
//            order.setShop(shop);//TODO Как правильно хранить Магазин?

            connection.commit();
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
        Shop shop = null;
        String query = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            ResultSet resultSet = statement.getResultSet();
            orders = new ArrayList<>();
            while (resultSet.next()) {
                order = new Order(
                        resultSet.getLong("id"),
                        buyerDAO.findById(resultSet.getLong("buyer_id")),
                        shopDAO.getById(resultSet.getLong("shop_id")),
                        new ArrayList<Product>());
//                order.setShop(shop);
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
