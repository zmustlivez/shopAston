package config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class JDBCConnectionConfig {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    public static Connection getJDBCConnection() {
        String PROPERTIES_FILE = "application.properties";
        Connection connection = null;
        try (InputStream fis = JDBCConnectionConfig.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(fis);

            URL = properties.getProperty("spring.datasource.url");
            USERNAME = properties.getProperty("spring.datasource.username");
            PASSWORD = properties.getProperty("spring.datasource.password");

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException | IOException e) {
            System.out.println("Connection failed");
            log.error(e.getMessage(), e);
        }
        return connection;
    }
}
