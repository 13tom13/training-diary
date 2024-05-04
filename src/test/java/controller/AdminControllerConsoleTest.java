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
    void setUp() {
        testUsers = new ArrayList<>();
        testUserDTO = new UserDTO();
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
        testUserDTO.setEmail("john@example.com");
        testUserDTO.setActive(true);
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
    public void testChangeUserData() {
        // Arrange
        String newName = "Jane";
        String newLastName = "Smith";

        User user = new User();
        user.setActive(true);
        when(userRepositoryMock.getUserByEmail(testUserDTO.getEmail())).thenReturn(Optional.of(user));

        // Act
        adminController.changeUserName(testUserDTO, newName);
        adminController.changeUserLastName(testUserDTO, newLastName);
        adminController.changeUserActive(testUserDTO);

        // Assert
        assertEquals(newName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertFalse(user.isActive());
    }


    @Test
    public void testChangeUserRights() {
        // Arrange
        List<Rights> newUserRights = new ArrayList<>();
        newUserRights.add(new Rights(1L, "WRITE"));

        User user = new User();
        when(userRepositoryMock.getUserByEmail(testUserDTO.getEmail())).thenReturn(Optional.of(user));

        // Act
        adminController.changeUserRights(testUserDTO, newUserRights);

        // Assert
        assertEquals(newUserRights, user.getRights());
    }

}
