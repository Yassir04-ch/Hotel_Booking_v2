package db;
import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private final Connection connection;

    private DatabaseConnection(){
        try{
            DatabaseConfig config =  DatabaseConfig.getInstance();
            connection = DriverManager.getConnection(config.getUrl() , config.getUsername() , config.getPassword());
        }catch (SQLException e){
            throw new RuntimeException("Erreur : "+ e.getMessage());
        }
    }



}
