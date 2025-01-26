package dao.impl;

import config.JDBCConnectionConfig;
import dao.BuyerDAO;
import entity.Buyer;
import entity.Order;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BuyerDAOImpl implements BuyerDAO {

    private final Connection connection;

    public BuyerDAOImpl() {
        this.connection = JDBCConnectionConfig.getJDBCConnection();
        if (this.connection == null) {
            throw new RuntimeException("Exception from DAO while connecting to database");
        }
    }

    @Override
    public Buyer create(Buyer buyer) {

        log.info("SQLQuery for Creating buyer is called");
        String sql = "INSERT INTO buyer (name, card_number, sale_value) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, buyer.getName());
            statement.setLong(2, buyer.getCardNumber());
            statement.setLong(3, buyer.getSaleValue());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    buyer.setId(generatedKeys.getLong(1));
                    log.info("Successfully generated buyer id: {}", buyer.getId());
                }
            }
        } catch (SQLException e) {
            log.error("SQLException while creating buyer", e);
            throw new RuntimeException(e);
        }
        log.info("Buyer created successfully");
        return buyer;
    }

    @Override
    public Buyer read(long id) {

        log.info("SQLQuery for Reading buyer is called");
        String sql = "SELECT * FROM buyer WHERE id = ?";

        Buyer buyer = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    buyer = new Buyer();
                    buyer.setId(resultSet.getLong("id"));
                    buyer.setName(resultSet.getString("name"));
                    buyer.setCardNumber(resultSet.getLong("card_number"));
                    buyer.setSaleValue(resultSet.getLong("sale_value"));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException while reading buyer", e);
            throw new RuntimeException(e);
        }
        log.info("Buyer read successfully");
        return buyer;
    }

    @Override
    public void update(Buyer buyer) {

        log.info("SQLQuery for Updating buyer is called");
        String sql = "UPDATE buyer SET name = ?, card_number = ?, sale_value = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buyer.getName());
            statement.setLong(2, buyer.getCardNumber());
            statement.setLong(3, buyer.getSaleValue());
            statement.setLong(4, buyer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException while updating buyer", e);
            throw new RuntimeException(e);
        }
        log.info("Buyer updated successfully");
    }

    @Override
    public void delete(long id) {

        log.info("SQLQuery for Deleting buyer is called");
        String sql = "DELETE FROM buyer WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException while deleting buyer", e);
            throw new RuntimeException(e);
        }
        log.info("Buyer deleted successfully");
    }

    @Override
    public List<Buyer> findAll() {

        log.info("SQLQuery for Finding all buyers is called");
        String sql = "SELECT * FROM buyer";

        List<Buyer> buyers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Buyer buyer = new Buyer();
                buyer.setId(resultSet.getLong("id"));
                buyer.setName(resultSet.getString("name"));
                buyer.setCardNumber(resultSet.getLong("card_number"));
                buyer.setSaleValue(resultSet.getLong("sale_value"));
                buyers.add(buyer);
            }
        } catch (SQLException e) {
            log.error("SQLException while Finding all buyers", e);
            throw new RuntimeException(e);
        }
        log.info("All buyers found successfully");
        return buyers;
    }
}
