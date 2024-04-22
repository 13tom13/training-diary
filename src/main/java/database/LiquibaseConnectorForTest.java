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

public class LiquibaseConnectorForTest {
//    private String url = getTestDbUrl();
//    private String username = getTestDbUsername();
//    private String password = getTestDbPassword();
    private String changelogFilePath = getTestChangeLogFile();


    public LiquibaseConnectorForTest() {
    }

    public void runMigrations(Connection connection) {
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Liquibase for test update executed successfully.\n");

        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
