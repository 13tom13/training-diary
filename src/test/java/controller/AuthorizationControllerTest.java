package controller;

import exceptions.security.AuthorizationException;
import in.controller.authorization.implementation.AuthorizationControllerImpl;
import in.service.users.AuthorizationService;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest extends TestUtil {

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private AuthorizationControllerImpl authorizationController;

    @Test
    void loginValidCredentialsReturnsUser() throws AuthorizationException {
        // Arrange
        User expectedUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);

        // Stubbing the service method
        Mockito.when(authorizationService.login(TEST_EMAIL, TEST_PASSWORD)).thenReturn(expectedUser);

        // Act
        User actualUser = authorizationController.login(TEST_EMAIL, TEST_PASSWORD);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void loginInvalidCredentialsThrowsAuthorizationException() throws AuthorizationException {
        // Arrange
        String password = "wrongpass";

        // Stubbing the service method to throw an exception
        Mockito.when(authorizationService.login(TEST_EMAIL, password)).thenThrow(AuthorizationException.class);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationController.login(TEST_EMAIL, password));
    }
}

