package dao.impl;

import config.JDBCConnectionConfig;
import dao.BuyerDAO;
import entity.Buyer;
import entity.Order;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
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
    public Buyer createBuyer(Buyer buyer) {

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
    public Buyer readBuyer(long id) {
        return null;
    }

    @Override
    public Buyer updateBuyer(Buyer buyer) {
        return null;
    }

    @Override
    public void deleteBuyer(long id) {
    }

    @Override
    public List<Buyer> findAllBuyers() {
        return List.of();
    }

    @Override
    public Optional<Order> findOrderByBuyerId(long id) {
        return Optional.empty();
    }

}
