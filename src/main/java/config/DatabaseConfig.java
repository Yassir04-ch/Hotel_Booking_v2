package config;

public class DatabaseConfig {

    private static DatabaseConfig instance;

    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_booking";
    private static final String USERNAME = "yassirch";
    private static final String PASSWORD = "yassir123";

    public DatabaseConfig() {
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public String getUrl() {
        return URL;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }
}