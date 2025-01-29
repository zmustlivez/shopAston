package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnectionConfig {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;


    public static Connection getJDBCConnection() {
        Connection connection = null;
        try {
            Properties properties = new Properties();
            /*URL = properties.getProperty("spring.datasource.url");
            USERNAME = properties.getProperty("spring.datasource.username");
            PASSWORD = properties.getProperty("spring.datasource.password");*/
            URL = "jdbc:postgresql://localhost:5432/postgres";
            USERNAME = "postgres";
            PASSWORD = "password";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return connection;
    }
}
