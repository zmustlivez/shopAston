package config;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class JDBCConnectionConfig {

    private static final String PROPERTIES_FILE = "application.properties";
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        log.info("Loading config file and parameters from application.properties");

        try (InputStream input = JDBCConnectionConfig.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            if (input == null) {
                throw new RuntimeException("Configuration: " + PROPERTIES_FILE + " not found");
            }
            log.info("Successfully load config file from application.properties");

            properties.load(input);

            URL = properties.getProperty("spring.datasource.url");
            USERNAME = properties.getProperty("spring.datasource.username");
            PASSWORD = properties.getProperty("spring.datasource.password");

            log.info("Successfully load parameters from application.properties");

        } catch (Exception e) {
            throw new RuntimeException("Exception while loading config file and parameters from application.properties");
        }
        log.info("Successfully load config file and parameters from application.properties");
    }

    public static Connection getJDBCConnection() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            log.error("SQLException while connecting to database", e);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException while loading driver for JDBC", e);
            throw new RuntimeException(e);
        }

        log.info("Successfully connected to JDBC database");
        return connection;
    }

}
