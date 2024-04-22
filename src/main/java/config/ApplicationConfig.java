package config;

import database.LiquibaseConnector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final String CONFIG_FILE = "/application.properties";

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ApplicationConfig.class.getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            } else {
                throw new IOException("Unable to load config file: " + CONFIG_FILE);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файла конфигурации: " + e.getMessage());
        }
        liquibaseMigrations();
    }

    private static void liquibaseMigrations() {
        LiquibaseConnector connector = new LiquibaseConnector();
        connector.runMigrations();
    }

    public static String getDbUrl() {
        return PROPERTIES.getProperty("db.url");
    }

    public static String getDbUsername() {
        return PROPERTIES.getProperty("db.username");
    }

    public static String getDbPassword() {
        return PROPERTIES.getProperty("db.password");
    }

    public static String getChangeLogFile() {
        return PROPERTIES.getProperty("liquibase.changelog");
    }

    public static String getTestDatabaseName() {
        return PROPERTIES.getProperty("db.test.testDatabaseName");
    }

    public static String getTestDbUsername() {
        return PROPERTIES.getProperty("db.test.username");
    }

    public static String getTestDbPassword() {
        return PROPERTIES.getProperty("db.test.password");
    }

    public static String getTestChangeLogFile() {
        return PROPERTIES.getProperty("liquibase.test.changelog");
    }
}