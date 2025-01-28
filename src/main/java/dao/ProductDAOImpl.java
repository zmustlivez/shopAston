package dao;

import entities.Product;
import config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Connection connection = Config.getJDBCConnection();

    @Override
    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS product " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45), " +
                //определиться с типами
                "price," +
                "expire_date)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы продуктов");
        }
    }

    @Override
    public void dropTable() {
        String dropTableSql = "DROP TABLE IF EXISTS product";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableSql);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы продуктов");
        }
    }

    @Override
    public void clearTable() {
        String dropTableSql = "DELETE * from TABLE product";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableSql);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы продуктов");
        }
    }

    @Override
    public boolean create(Product product) {
        String insertSql = "INSERT INTO table product " +
                "(name, price, expireDate) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            //задаем параметры
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setDate(3, Date.valueOf(product.getExpiryDate()));
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            // Если возникла ошибка, откатываем изменения
            rollbackChanges(e);
            return false;
        }
    }

    private void rollbackChanges(SQLException e) {
        try {
            connection.rollback();
            //вообще это лучше в лог писать
            System.out.println("Изменения откачены из-за ошибки бд" + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("Откат изменений выполнить не удалось" + ex.getMessage());
        }
    }

    @Override
    public Product getById(Long id) {
        String selectSql = "SELECT id, name, price, expireDate " +
                "from product " +
                "where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            //задаем параметры
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product(id,
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiryDate").toLocalDate());
            } else return null;

        } catch (SQLException e) {
            // Если возникла ошибка, откатываем изменения

        }
        return null;
    }

    @Override
    public List<Product> getByName(String name) {
        //может сделать поиск по маске?
        String selectSql = "SELECT id, name, price, expireDate " +
                "from product " +
                "where name = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            //задаем параметры
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getInt(resultSet.getInt("id")),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiryDate").toLocalDate());
                products.add(product);
            }

        } catch (SQLException e) {
            //пишем в лог
        }
        return products;
    }

    @Override
    public int update(Product product) {
        int updatedRows = 0;
        String updateSql = "UPDATE product SET name = ?, price = ?, expiryDate = ? " +
                "where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setDate(3, Date.valueOf(product.getExpiryDate()));
            preparedStatement.setLong(4, product.getId());
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updatedRows;
    }

    @Override
    public List<Product> getAllProducts() {
        String selectSql = "SELECT id, name, price, expireDate " +
                "from product " ;
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            //может, эту часть вынести отдельно?
            while (resultSet.next()) {
                Product product = new Product(resultSet.getInt(resultSet.getInt("id")),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiryDate").toLocalDate());
                products.add(product);
            }

        } catch (SQLException e) {
            //пишем в лог
        }
        return products;
    }

    @Override
    public int delete(long id) {
        String deleteSql = "Delete " +
                "from product " +
                "where id = ?";
        int deletedRows = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
           deletedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deletedRows;
    }
}
