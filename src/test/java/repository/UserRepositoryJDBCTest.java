package repository;

import exceptions.RepositoryException;
import in.repository.user.implementation.UserRepositoryJDBC;
import model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import testutil.TestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryJDBCTest {

    private static PostgreSQLContainer<?> postgreSQLContainer;
    private Connection connection;
    private UserRepositoryJDBC userRepository;

    @BeforeAll
    static void setUpContainer() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("test-db")
                .withUsername("test-user")
                .withPassword("test-password")
                .withInitScript("init.sql");
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDownContainer() {
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
        }
    }

    @BeforeEach
    void setUp() throws SQLException {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        userRepository = new UserRepositoryJDBC(connection);
        // Очистка базы данных перед каждым тестом
        cleanDatabase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private void cleanDatabase() throws SQLException {
        // Например, можно удалять все записи из таблицы пользователей
        connection.prepareStatement("DELETE FROM users").executeUpdate();
    }

    @Test
    void testSaveUserAndGetUserByEmail() throws RepositoryException {
        // Arrange
        User user = new User(TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_EMAIL, TestUtil.TEST_PASSWORD);

        // Act
        userRepository.saveUser(user);
        Optional<User> retrievedUser = userRepository.getUserByEmail(user.getEmail());
        // Assert
        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get(), "Expected user does not match retrieved user");

    }

    @Test
    void testGetAllUsers() throws RepositoryException {
        // Arrange
        User user1 = new User(TestUtil.TEST_EMAIL, TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_PASSWORD);
        User user2 = new User("test2@example.com", "Jane", "Smith", "password2");
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        // Act
        List<User> userList = userRepository.getAllUsers();

        // Assert
        assertEquals(2, userList.size());
        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));
    }

    @Test
    void testUpdateUser() throws RepositoryException {
        // Arrange
        User user = new User(TestUtil.TEST_EMAIL, TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_PASSWORD);
        userRepository.saveUser(user);
        user.setFirstName("UpdatedFirstName");
        user.setLastName("UpdatedLastName");

        // Act
        userRepository.updateUser(user);
        Optional<User> updatedUser = userRepository.getUserByEmail(user.getEmail());

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(user, updatedUser.get());
    }

    @Test
    void testDeleteUser() throws RepositoryException {
        // Arrange
        User user = new User(TestUtil.TEST_EMAIL, TestUtil.TEST_FIRST_NAME, TestUtil.TEST_LAST_NAME, TestUtil.TEST_PASSWORD);
        userRepository.saveUser(user);

        // Act
        userRepository.deleteUser(user.getEmail());
        Optional<User> deletedUser = userRepository.getUserByEmail(user.getEmail());

        // Assert
        assertTrue(deletedUser.isEmpty());
    }
}
