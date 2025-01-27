package config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Config {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    public static Connection getJDBCConnection() {

        Connection connection = null;
        try (FileInputStream fis = new FileInputStream("/home/ml/IdeaProjects/ShopAston/src/main/resources/application.properties")) {
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
