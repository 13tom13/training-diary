package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static config.ApplicationConfig.*;

public class DatabaseConnection {
    private static final String URL = getDbUrl();
    private static final String USERNAME = getDbUsername();
    private static final String PASSWORD = getDbPassword();

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
