package controller;

import in.controller.users.implementation.AdminControllerImpl;
import in.repository.user.UserRepository;
import model.Rights;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testutil.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private AdminControllerImpl adminController;
    private List<User> testUsers;
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUsers = new ArrayList<>();
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        testUsers.add(testUser);
    }

    @Test
    public void testGetAllUsers_ReturnsAllUsers() {
        // Arrange
        when(userRepositoryMock.getAllUsers()).thenReturn(testUsers);

        // Act
        List<User> actualUsers = adminController.getAllUsers();

        // Assert
        assertEquals(testUsers, actualUsers);
    }

    @Test
    public void testGetUser_ReturnsUser_WhenUserExists() {
        // Arrange
        when(userRepositoryMock.getUserByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        User actualUser = adminController.getUser(testUser.getEmail());

        // Assert
        assertEquals(testUser, actualUser);
    }

    @Test
    public void testGetUser_ReturnsNull_WhenUserDoesNotExist() {
        // Arrange
        String nonexistentEmail = "nonexistent@example.com";
        when(userRepositoryMock.getUserByEmail(nonexistentEmail)).thenReturn(Optional.empty());

        // Act
        User actualUser = adminController.getUser(nonexistentEmail);

        // Assert
        assertNull(actualUser);
    }

    @Test
    public void testChangeUserName() {
        // Arrange
        String newName = "Jane";

        // Act
        adminController.changeUserName(testUser, newName);

        // Assert
        assertEquals(newName, testUser.getFirstName());
        verify(userRepositoryMock).updateUser(testUser);
    }

    @Test
    public void testChangeUserLastName() {
        // Arrange
        String newLastName = "Smith";

        // Act
        adminController.changeUserLastName(testUser, newLastName);

        // Assert
        assertEquals(newLastName, testUser.getLastName());
        verify(userRepositoryMock).updateUser(testUser);
    }

    @Test
    public void testChangeUserPassword() {
        // Arrange
        String newPassword = "newpassword";

        // Act
        adminController.changeUserPassword(testUser, newPassword);

        // Assert
        assertEquals(newPassword, testUser.getPassword());
        verify(userRepositoryMock).updateUser(testUser);
    }

    @Test
    public void testChangeUserActive() {
        // Act
        adminController.changeUserActive(testUser);

        // Assert
        assertFalse(testUser.isActive());
        verify(userRepositoryMock).updateUser(testUser);
    }


    @Test
    public void testChangeUserRights() {
        // Arrange
        List<Rights> newUserRights = new ArrayList<>();
        newUserRights.add(new Rights(1L,"WRITE"));

        // Act
        adminController.changeUserRights(testUser, newUserRights);

        // Assert
        assertEquals(newUserRights, testUser.getRights());
        verify(userRepositoryMock).updateUser(testUser);
    }


    @Test
    public void testDeleteUser() {
        // Act
        adminController.deleteUser(testUser);

        // Assert
        verify(userRepositoryMock).deleteUser(testUser);
    }
}

