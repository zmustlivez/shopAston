package ru.astondevs.shop.dao.impl;

import ru.astondevs.shop.dao.ProductDAO;
import ru.astondevs.shop.entity.Product;
import ru.astondevs.shop.config.JDBCConnectionConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductDAOImpl implements ProductDAO {
    private final Connection connection = JDBCConnectionConfig.getJDBCConnection();

    /**
     * Создание таблицы Продуктов с полями id, name, price, expiry_date
     * Метод может выбрасывать RuntimeException в случае неудачной попытки
     */
    @Override
    public void createTable() {

        String createTableSql = "CREATE TABLE IF NOT EXISTS product " +
                "(id SERIAL PRIMARY KEY," +
                "name VARCHAR(45) NOT NULL, " +
                //определиться с типами
                "price NUMERIC," +
                "expiry_date DATE)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            log.error("Ошибка при создании таблицы продуктов");
            throw new RuntimeException(e);//todo узнать у Дениса. не уверена. что переопределение исключения хорошая идея
        }
        log.info("Cоздание таблицы продуктов выполнено успешно");
    }

    /**
     * Удаление таблицы Продуктов
     * Метод может выбрасывать RuntimeException в случае неудачной попытки
     */
    @Override
    public void dropTable() {
        String dropTableSql = "DROP TABLE IF EXISTS product";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableSql);
        } catch (SQLException e) {
            log.error("Ошибка при удалении таблицы продуктов");
            throw new RuntimeException(e);
        }
        log.info("Удаление таблицы продуктов выполнено успешно");
    }

    /**
     * Удаление записей из таблицы Продуктов
     * Метод может выбрасывать RuntimeException в случае неудачной попытки
     */
    @Override
    public void clearTable() {
        String dropTableSql = "DELETE * from TABLE product";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableSql);
        } catch (SQLException e) {
            log.error("Ошибка при очищении таблицы продуктов");
            throw new RuntimeException(e);
        }
        log.info("Очищение таблицы продуктов выполнено успешно");
    }

    /**
     * Добавление продукта в таблицу
     * @param product - продукт
     * @return идентификатор добавленного продукта или -1, если неудачно
     */
    @Override
    public long create(Product product) {
      String insertSql = "INSERT INTO product " +
                "(name, price, expiry_date) VALUES (?,?,?)";
       try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            //задаем параметры
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setObject(3,product.getExpiryDate());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
           //todo возвращает -1, но вставляет
            long id = resultSet.next()? resultSet.getLong("id"):-1;
            log.info("Добавление продукта "+ id + " выполнено успешно");
            return id;
        } catch (SQLException e) {
            log.error("Ошибка при добавлении товара в таблицу продуктов");
            return -1;
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

    /**
     * Поиск продуктов по id. Метод может выбрасывать исключение
     * RuntimeException. Здесь это нужно, чтоб разграничивать ситуации: товар не найден
     * и что-то с подключением к БД
     *
     * @param id - идентификатор продукта
     * @return - информацию о продукте, если он найден и null если такого
     *          продукта нет в бд
     */
    @Override
    public Product getById(Long id) {
        String selectSql = "SELECT id, name, price, expiry_date " +
                "from product " +
                "where id = ?";
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            //задаем параметры
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(id,
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiry_date").toLocalDate());
            }

        } catch (SQLException e) {
            log.error("Ошибка при поиске продукта по id в таблице продуктов");
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Поиск(по маске) товаров по наименованию
     * @param name - наименование товара
     * @return список товаров с указанным наименованием
     */
    //TODO сделать маску в запросе
    @Override
    public List<Product> getByName(String name) {
        //может сделать поиск по маске?
        String selectSql = "SELECT id, name, price, expiry_date " +
                "from product " +
                "where name = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            //задаем параметры
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiry_date").toLocalDate());
                products.add(product);
            }

        } catch (SQLException e) {
            log.error("Ошибка при поиске продуктов по наименованию в таблице продуктов");
            throw new RuntimeException(e);
        }
        return products;
    }

    /**
     * Обновление информации о продукте
     * @param product - продукт
     * @return - количество обновленных записей (если равно нулю, то запись с таким идентификатором не нашлась)
     */
    //TODO возможно надо изменить наименование метода обновление по id.
    @Override
    public int update(Product product) {
        int updatedRows = 0;
        String updateSql = "UPDATE product SET name = ?, price = ?, expiry_date = ? " +
                " where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setDate(3, Date.valueOf(product.getExpiryDate()));
            preparedStatement.setLong(4, product.getId());
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка при обновлении продукта c идентификатором " + product.getId());
            throw new RuntimeException(e);
        }
        log.info("Продукт " + product.getId()+ "успешно обновлен");
        return updatedRows;
    }

    /**
     * Получение списка всех продуктов
     * @return список всех продуктов
     */
    @Override
    public List<Product> getAllProducts() {
        String selectSql = "SELECT id, name, price, expiry_date " +
                "from product ";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiry_date").toLocalDate());
                products.add(product);
            }

        } catch (SQLException e) {
            log.error("Ошибка при получении списка продуктов" );
            throw new RuntimeException(e);
        }
        return products;
    }

    /**
     * Удаление продукта по его идентификатору
     * @param id - идентификатор продукта
     * @return количество удаленных строк
     */
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
            log.error("Ошибка при удалении продукта c идентификатором " + id);
            throw new RuntimeException(e);
        }
        return deletedRows;
    }
}
