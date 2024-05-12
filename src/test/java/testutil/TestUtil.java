package testutil;

import database.LiquibaseConnectorForTest;
import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.Roles;
import entity.model.User;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static config.ApplicationConfig.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Logger.getLogFielPath;

/**
 * Утилитарный класс для тестирования.
 */
public abstract class TestUtil {

    /**
     * Тестовое имя пользователя.
     */
    public static final String TEST_FIRST_NAME = "John";

    /**
     * Тестовая фамилия пользователя.
     */
    public static final String TEST_LAST_NAME = "Doe";

    /**
     * Тестовый email.
     */
    public static final String TEST_EMAIL = "test@gmail.com";

    /**
     * Тестовый пароль.
     */
    public static final String TEST_PASSWORD = "password";

    /**
     * Тестовая дата.
     */
    public static final LocalDate TEST_DATE = LocalDate.now().minusDays(1);

    private static final String databaseName = getTestDatabaseName();
    private static final String username = getTestDbUsername();
    private static final String password = getTestDbPassword();

    /**
     * Метод, выполняющийся после всех тестов для очистки журналов.
     */
    public static void cleanLogsAfterTests() {
        // Получаем путь к файлу журнала для тестового email
        String logFilePath = getLogFielPath(TEST_EMAIL);

        // Создаем объект файла для удаления
        File logFile = new File(logFilePath);

        // Проверяем существует ли файл журнала
        if (logFile.exists()) {
            // Удаляем файл журнала
            assertTrue(logFile.delete(), "Failed to delete log file");
        }
    }

    public static UserDTO createTestUserDTO() {
        UserDTO testUserDTO = new UserDTO();
//        testUserDTO.setId(1L);
//        testUserDTO.setFirstName("John");
//        testUserDTO.setLastName("Doe");
//        testUserDTO.setEmail("john@example.com");
//        testUserDTO.setActive(true);
//        testUserDTO.setRights(new ArrayList<>());
//        testUserDTO.setRoles(new ArrayList<>());
//        testUserDTO.setActive(true);
//        testUserDTO.getRoles().add(new Roles(2L, "USER"));
//        testUserDTO.getRights().add(new Rights(4L, "STATISTICS"));
        return testUserDTO;
    }

    public static TrainingDTO createTestTrainingDTO() {
        TrainingDTO testTrainingDTO = TrainingDTO.builder().build();
//        testTrainingDTO.setId(1L);
//        testTrainingDTO.setName("Test Training");
//        testTrainingDTO.setDate(LocalDate.now().minusDays(1));
//        testTrainingDTO.setDuration(60);
//        testTrainingDTO.setCaloriesBurned(500);
//        testTrainingDTO.setAdditions(new HashMap<>());
//        testTrainingDTO.getAdditions().put("Location", "Gym");
//        testTrainingDTO.getAdditions().put("Weather", "Sunny");

        return testTrainingDTO;
    }

    public static User createTestUser() {
        User testUser = new User();
//        testUser.setFirstName("John");
//        testUser.setLastName("Doe");
//        testUser.setEmail("john@example.com");
//        testUser.setActive(true);
//        testUser.setRights(new ArrayList<>());
//        testUser.setRoles(new ArrayList<>());
//        testUser.setActive(true);
//        testUser.getRoles().add(new Roles(2L, "USER"));
//        testUser.getRights().add(new Rights(4L, "STATISTICS"));
        return testUser;
    }

    public static Connection createConnectionToTestDatabase() throws SQLException {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName(databaseName)
                .withUsername(username)
                .withPassword(password)
                .withExposedPorts(5432);
        postgres.start();

        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        createTestSchemas(connection);

        LiquibaseConnectorForTest connectorForTestDB = new LiquibaseConnectorForTest();
        connectorForTestDB.runMigrations(connection);

        return connection;
    }

    public static void createTestSchemas(Connection connection) throws SQLException {
        String sql = "CREATE SCHEMA IF NOT EXISTS main";
        String sql1 = "CREATE SCHEMA IF NOT EXISTS permissions";
        String sql2 = "CREATE SCHEMA IF NOT EXISTS service";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            // Обработка ошибок, если таковые возникнут при выполнении запросов
            throw new SQLException("Ошибка при создании схемы: " + e.getMessage());
        }
    }


    /**
     * Метод для получения тестового пользователя из базы данных.
     * Используется для получения объекта пользователя для использования в тестах.
     *
     * @return объект User, представляющий тестового пользователя
     * @throws SQLException если возникает ошибка при выполнении запроса к базе данных
     */
    public static User getTestUserFromDatabase(Connection connection) throws SQLException {
        // Запрос для получения первого пользователя из таблицы пользователей
        String sql = "SELECT * FROM main.users LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Создание и возвращение объекта User
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                } else {
                    throw new SQLException("Тестовый пользователь не найден в базе данных");
                }
            }
        }
    }

}
