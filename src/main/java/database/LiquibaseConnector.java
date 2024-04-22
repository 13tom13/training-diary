package database;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static config.ApplicationConfig.*;

public class LiquibaseConnector {
    private String url = getDbUrl();
    private String username = getDbUsername();
    private String password = getDbPassword();
    private String changelogFilePath = getChangeLogFile();


    public LiquibaseConnector() {
    }

    public void runMigrations() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            // Устанавливаем новую схему по умолчанию
            database.setDefaultSchemaName("service");

            Liquibase liquibase = new Liquibase(changelogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Liquibase update executed successfully.\n");

        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
