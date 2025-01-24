package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Config {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;


    static Connection getJDBCConnection() {
        Connection connection = null;
        try {
            Properties properties = new Properties();
            URL = properties.getProperty("spring.datasource.url");
            USERNAME = properties.getProperty("spring.datasource.username");
            PASSWORD = properties.getProperty("spring.datasource.password");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return connection;
    }
}
