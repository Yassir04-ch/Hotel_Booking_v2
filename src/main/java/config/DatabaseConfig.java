package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static DatabaseConfig instance;

    private static String url = "jdbc:postgresql://localhost:5432/hotel_booking";
    private static String username = "yassirch";
    private static String password = "yassir123";

    private DatabaseConfig(){
    }

    public static DatabaseConfig getInstance(){
        if(instance == null){
             instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
