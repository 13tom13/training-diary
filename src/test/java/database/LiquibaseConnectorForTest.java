package database;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

import static config.ApplicationConfig.getTestChangeLogFile;

public class LiquibaseConnectorForTest {
    private final String changelogFilePath = getTestChangeLogFile();


    public LiquibaseConnectorForTest() {
    }

    public void runMigrations(Connection connection) {
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Liquibase for test update executed successfully.\n");

        } catch (LiquibaseException e) {
            System.out.println("Liquibase update test with exception");
        }
    }
}
