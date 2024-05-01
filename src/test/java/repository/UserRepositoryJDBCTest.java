package repository;

import entities.model.User;
import exceptions.RepositoryException;
import in.repository.user.UserRepository;
import in.repository.user.implementation.UserRepositoryJDBC;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testutil.TestUtil.*;

public class UserRepositoryJDBCTest {

    private static Connection connection;
    private static UserRepository userRepository;
    private static User user;

    @BeforeAll
    static void setUpContainer() throws SQLException {
        connection = createConnectionToTestDatabase();
        userRepository = new UserRepositoryJDBC(connection);
        user = getTestUserFromDatabase(connection);
    }

    @AfterAll
    static void tearDownContainer() throws SQLException {
        cleanLogsAfterTests();
        connection.close();

    }


    @Test
    void testSaveUserAndGetUserByEmail() throws RepositoryException {
        // Arrange
        User user = new User("Test", "Test", "test2@test.com", "test");

        // Act
        userRepository.saveUser(user);
        Optional<User> retrievedUser = userRepository.getUserByEmail(user.getEmail());
        // Assert
        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get(), "Expected user does not match retrieved user");

    }

    @Test
    void testGetAllUsers() {
        // Act
        List<User> userList = userRepository.getAllUsers();

        // Assert
        assertEquals(1, userList.size());
        assertTrue(userList.contains(user));
    }

    @Test
    void testUpdateUser() {
        // Arrange
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
    void testDeleteUser() {

        // Act
        userRepository.deleteUser(user);
        Optional<User> deletedUser = userRepository.getUserByEmail(user.getEmail());

        // Assert
        assertTrue(deletedUser.isEmpty());
    }
}
