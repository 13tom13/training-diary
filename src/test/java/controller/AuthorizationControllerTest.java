package controller;

import in.controller.authorization.implementation.AuthorizationControllerImpl;
import exceptions.security.AuthorizationException;
import model.User;
import in.service.users.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестирование класса AuthorizationControllerImpl.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorizationControllerTest extends TestUtil {

    private AuthorizationControllerImpl authorizationController;
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = Mockito.mock(AuthorizationService.class);
        authorizationController = new AuthorizationControllerImpl(authorizationService);
    }

    /**
     * Тестирование метода login с правильными учетными данными.
     *
     * @throws AuthorizationException если возникла ошибка авторизации
     */
    @Test
    void loginValidCredentialsReturnsUser() throws AuthorizationException {
        // Arrange
        String firstname = "Test";
        String lastname = "User";
        String password = "pass";
        User expectedUser = new User(firstname, lastname, TEST_EMAIL, password);

        // Stubbing the service method
        Mockito.when(authorizationService.login(TEST_EMAIL, password)).thenReturn(expectedUser);

        // Act
        User actualUser = authorizationController.login(TEST_EMAIL, password);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    /**
     * Тестирование метода login с неправильными учетными данными.
     *
     * @throws AuthorizationException если возникла ошибка авторизации
     */
    @Test
    void loginInvalidCredentialsThrowsAuthorizationException() throws AuthorizationException {
        // Arrange
        String email = "test@mail.ru";
        String password = "wrongpass";

        // Stubbing the service method to throw an exception
        Mockito.when(authorizationService.login(email, password)).thenThrow(AuthorizationException.class);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationController.login(email, password));
    }
}

