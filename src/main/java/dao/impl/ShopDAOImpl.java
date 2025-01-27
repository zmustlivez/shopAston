package dao.impl;

import config.JDBCConnectionConfig;
import dao.ShopDAO;
import entity.Order;
import entity.Product;
import entity.Shop;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ShopDAOImpl implements ShopDAO {

    private Connection connection;


    public ShopDAOImpl(Connection connection) {

        if (connection != null){
            this.connection = connection;
        } else this.connection = JDBCConnectionConfig.getJDBCConnection();

    }

    @Override
    public void createShopTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS SHOP (" +
                    "id SERIAL PRIMARY KEY," +
                    " name varchar(50) NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while creating shop");
        }
        log.info("Table SHOP created successfully");

    }
    @Override
    public void dropShopTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS SHOP");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while dropping shop");
        }
        log.info("Table Shop dropped successfully");

    }

    @Override
    public void clearShopTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE IF EXISTS SHOP");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while clearing shop");
        }
        log.info("Table Shop clearance successful");

    }

    @Override
    public void saveShop(Shop shop) {

        String sql = "INSERT INTO IF EXISTS SHOP (name) values (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, shop.getName());
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while saving shop");
        }
        log.info("Shop saving successful");
    }

    @Override
    public List<Shop> getShops() {
        List<Shop> shops = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM IF EXISTS SHOP");
            while (resultSet.next()){
                Shop shop = new Shop();
                shop.setId(resultSet.getLong("id"));
                shop.setName(resultSet.getString("name"));
                shops.add(shop);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while extracting shops");
        }
        log.info("Shops extracting successful");
        return shops;
    }

    @Override
    public Shop getShopByName(String name) {

        Shop shop = new Shop();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM IF EXISTS SHOP WHERE name = ?")){

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                shop.setId(resultSet.getLong("id"));
                shop.setName(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("SQLException while extractingByName shops");
        }

        log.info("Shops extractingByName successful");

        return shop;
    }

    @Override
    public Shop getShopById(int id) {
        Shop shop = new Shop();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM IF EXISTS SHOP WHERE id = ?")){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                shop.setId(resultSet.getLong("id"));
                shop.setName(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("SQLException while extractingByID shops");
        }

        log.info("Shops extractingByID successful");

        return shop;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public void updateShop(Shop shop) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE IF EXISTS SHOP SET name = ? WHERE id = ?")){
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setLong(2, shop.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info("Shop updating successful");
    }
}
