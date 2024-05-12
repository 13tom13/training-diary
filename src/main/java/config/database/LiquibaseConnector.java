package config.database;

import jakarta.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static config.ApplicationConfig.*;

@Component
public class LiquibaseConnector {


    private final DataSource dataSource;


    private String changelogFilePath = "db/changelog/changelog.xml";

    private String schema = "service";
    @Autowired
    public LiquibaseConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void runMigrations() {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            createDefaultSchema(connection, schema);
            database.setLiquibaseSchemaName(schema);

            Liquibase liquibase = new Liquibase(changelogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Liquibase update executed successfully.\n");

        } catch (SQLException | LiquibaseException e) {
            System.out.println("Liquibase update with exception" + e.getMessage());
        }
    }

    private void createDefaultSchema(Connection connection, String schemaName) throws SQLException {
        String sql = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

}
