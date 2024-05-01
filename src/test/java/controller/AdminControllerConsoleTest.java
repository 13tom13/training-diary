package controller;

import entities.dto.UserDTO;
import entities.model.Rights;
import entities.model.User;
import exceptions.UserNotFoundException;
import in.controller.users.implementation.AdminControllerConsole;
import in.repository.user.UserRepository;
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

@ExtendWith(MockitoExtension.class)
public class AdminControllerConsoleTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private AdminControllerConsole adminController;

    private List<User> testUsers;
    private UserDTO testUserDTO;

    @BeforeEach
    public void setUp() {
        testUsers = new ArrayList<>();
        testUserDTO = new UserDTO(1L, "John", "Doe", "john@example.com", null, null, true);
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
    public void testGetUser_ReturnsUserDTO_WhenUserExists() throws UserNotFoundException {
        // Arrange
        String userEmail = "john@example.com";
        when(userRepositoryMock.getUserByEmail(userEmail)).thenReturn(Optional.of(new User()));

        // Act
        UserDTO actualUserDTO = adminController.getUser(userEmail);

        // Assert
        assertNotNull(actualUserDTO);
    }

    @Test
    public void testGetUser_ThrowsException_WhenUserDoesNotExist() {
        // Arrange
        String nonexistentEmail = "nonexistent@example.com";
        when(userRepositoryMock.getUserByEmail(nonexistentEmail)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class, () -> adminController.getUser(nonexistentEmail));
    }

    @Test
    public void testChangeUserName() {
        // Arrange
        String newName = "Jane";

        // Act
        adminController.changeUserName(testUserDTO, newName);

        // Assert
        assertEquals(newName, testUserDTO.getFirstName());
    }

    @Test
    public void testChangeUserLastName() {
        // Arrange
        String newLastName = "Smith";

        // Act
        adminController.changeUserLastName(testUserDTO, newLastName);

        // Assert
        assertEquals(newLastName, testUserDTO.getLastName());
    }

    @Test
    public void testChangeUserActive() {
        // Act
        adminController.changeUserActive(testUserDTO);

        // Assert
        assertFalse(testUserDTO.isActive());
    }

    @Test
    public void testChangeUserRights() {
        // Arrange
        List<Rights> newUserRights = new ArrayList<>();
        newUserRights.add(new Rights(1L, "WRITE"));

        // Act
        adminController.changeUserRights(testUserDTO, newUserRights);

        // Assert
        assertEquals(newUserRights, testUserDTO.getRights());
    }

}
