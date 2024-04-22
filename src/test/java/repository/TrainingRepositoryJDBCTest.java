package repository;

import exceptions.RepositoryException;
import in.repository.training.implementation.TrainingRepositoryJDBC;
import model.Training;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import testutil.TestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeSet;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

public class TrainingRepositoryJDBCTest {

    private static Connection connection;
    private static TrainingRepositoryJDBC repository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("training-diary-test")
                .withUsername("test")
                .withPassword("test");
        postgres.start();

        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        connection = DriverManager.getConnection(jdbcUrl, username, password);
        repository = new TrainingRepositoryJDBC(connection);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }

    @AfterAll
    static void cleanLogsAfterTests() {
        // Clean logs after tests using TestUtil
        TestUtil.cleanLogsAfterTests();
    }

    @Test
    void testGetAllTrainingsByUserID() {
        User user = new User(TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_EMAIL, TestUtil.TEST_PASSWORD);
        user.setId(1);
        TreeMap<Date, TreeSet<Training>> result = repository.getAllTrainingsByUserID(user);
        assertThat(result).isNotNull();
        assertThat(result).isEmpty(); // Assuming no data in test setup
    }

    @Test
    void testGetTrainingsByUserIDAndData() throws RepositoryException {
        User user = new User(TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_EMAIL, TestUtil.TEST_PASSWORD);
        user.setId(1);
        Date date = new Date();
        TreeSet<Training> result = repository.getTrainingsByUserIDAndData(user, date);
        assertThat(result).isNotNull();
        assertThat(result).isEmpty(); // Assuming no data in test setup
    }

    @Test
    void testGetTrainingByUserIDlAndDataAndName() {
        User user = new User(TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_EMAIL, TestUtil.TEST_PASSWORD);
        user.setId(1);
        Date date = new Date();
        String name = "Test Training";
        try {
            repository.getTrainingByUserIDlAndDataAndName(user, date, name);
        } catch (RepositoryException e) {
            assertThat(e.getMessage()).isEqualTo("Тренировка с именем " + name + " не найдена в тренировках с датой " + date + " для пользователя с email " + user.getEmail());
        }
    }
}
