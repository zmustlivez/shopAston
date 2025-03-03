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


    public ShopDAOImpl() {
        this.connection = JDBCConnectionConfig.getJDBCConnection();
        if (connection == null){
            throw new RuntimeException("Error in connection");
        }

    }

    @Override
    public void createShopTable() {

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS SHOP (" +
                    "id SERIAL PRIMARY KEY," +
                    " name varchar(45) NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while creating shop");
        }
        log.info("Table SHOP created successfully");

    }
    @Override
    public void dropShopTable() {

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS SHOP");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while dropping shop");
        }
        log.info("Table Shop dropped successfully");

    }

    @Override
    public void clearShopTable() {

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE SHOP");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while clearing shop");
        }
        log.info("Table Shop clearance successful");

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void create(Shop shop) {

        createShopTable();

        String sql = "INSERT INTO SHOP (name) values (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, shop.getName());
            int i = preparedStatement.executeUpdate();
            if (i > 0){
                System.out.println("New line added");
            }else System.out.println("error");
        } catch (SQLException e) {
            throw new RuntimeException("SQLException while saving shop");
        }
        log.info("Shop saving successful");
    }

    @Override
    public List<Shop> findAll() {
        List<Shop> shops = new ArrayList<>();
        try (Statement statement = connection.createStatement()){

            statement.executeQuery("SELECT shop.id, shop.name FROM SHOP");
            ResultSet resultSet = statement.getResultSet();
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SHOP WHERE name = ?")){

            preparedStatement.setString(1, name);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()){
                shop.setId(resultSet.getLong("id"));
                shop.setName(resultSet.getString("name"));
            }
            if(shop.getName() == null){
                System.out.println("запись не найдена");
            }

        } catch (SQLException e) {
            throw new RuntimeException("SQLException while extractingByName shops");
        }

        log.info("Shops extractingByName successful");

        return shop;
    }

    @Override
    public Shop read(long id) {
        Shop shop = new Shop();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SHOP WHERE id = ?")){

            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()){
                shop.setId(resultSet.getLong("id"));
                shop.setName(resultSet.getString("name"));
            }

            if(shop.getName() == null){
                System.out.println("запись не найдена");
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
    public void update(Shop shop) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SHOP SET name = ? WHERE id = ?")){
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setLong(2, shop.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info("Shop updating successful");
    }
}
