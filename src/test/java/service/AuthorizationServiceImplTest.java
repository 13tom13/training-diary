package service;

import exceptions.security.AuthorizationException;
import exceptions.security.NotActiveUserException;
import in.repository.UserRepository;
import in.service.users.implementation.AuthorizationServiceImpl;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Тестирование класса AuthorizationServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest extends TestUtil {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;


    /**
     * Тестирование успешного входа пользователя в систему.
     *
     * @throws AuthorizationException если аутентификация не удалась из-за неверных учетных данных
     * @throws NotActiveUserException если пользователь не активен
     */
    @Test
    public void testLogin_Successful() throws AuthorizationException, NotActiveUserException {
        // Arrange
        User activeUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        activeUser.setActive(true);

        // Configure mock behavior
        when(userRepository.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(activeUser));

        // Act
        User loggedInUser = authorizationService.login(TEST_EMAIL, TEST_PASSWORD);

        // Assert
        assertNotNull(loggedInUser);
        assertEquals(activeUser, loggedInUser);
        assertTrue(loggedInUser.isActive());
    }

    /**
     * Тестирование входа пользователя в систему с неверным паролем.
     */
    @Test
    public void testLogin_WrongPassword() {
        // Arrange
        User user = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        user.setActive(true);

        // Configure mock behavior
        when(userRepository.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationService.login(TEST_EMAIL, "wrongPassword"));
    }

    /**
     * Тестирование входа пользователя в систему с неправильным email.
     */
    @Test
    public void testLogin_UserNotFound() {
        // Configure mock behavior
        when(userRepository.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationService.login(TEST_EMAIL, TEST_PASSWORD));
    }

    /**
     * Тестирование входа пользователя в систему, если пользователь не активен.
     */
    @Test
    public void testLogin_InactiveUser() {
        // Arrange
        User inactiveUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        inactiveUser.setActive(false);

        // Configure mock behavior
        when(userRepository.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(inactiveUser));

        // Act & Assert
        assertThrows(NotActiveUserException.class, () -> authorizationService.login(TEST_EMAIL, TEST_PASSWORD));
    }
}
