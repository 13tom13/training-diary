package controller;

import in.controller.AuthorizationController;
import in.exception.security.AuthorizationException;
import in.model.User;
import in.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorizationControllerTest  extends TestUtil {

    private AuthorizationController authorizationController;
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = Mockito.mock(AuthorizationService.class);
        authorizationController = new AuthorizationController(authorizationService);
    }

    @Test
    void loginValidCredentialsReturnsUser() throws AuthorizationException {
        // Arrange
        String firstname = "Test";
        String lastname = "User";
        String password = "pass";
        User expectedUser = new User(firstname, lastname,TEST_EMAIL, password);

        // Stubbing the service method
        Mockito.when(authorizationService.login(TEST_EMAIL, password)).thenReturn(expectedUser);

        // Act
        User actualUser = authorizationController.login(TEST_EMAIL, password);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

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