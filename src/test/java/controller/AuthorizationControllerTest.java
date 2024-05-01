package controller;

import entities.dto.AuthorizationDTO;
import entities.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.controller.authorization.implementation.AuthorizationControllerConsole;
import in.service.users.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest extends TestUtil {

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private AuthorizationControllerConsole authorizationController;

    @Test
    void loginValidCredentialsReturnsUser() throws AuthorizationException {
        // Arrange
        UserDTO expectedUser = new UserDTO(1L,TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, new ArrayList<>(),new ArrayList<>(),true);

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(TEST_EMAIL, TEST_PASSWORD);

        // Stubbing the service method
        Mockito.when(authorizationService.login(TEST_EMAIL, TEST_PASSWORD)).thenReturn(expectedUser);

        // Act
        UserDTO actualUser = authorizationController.login(authorizationDTO);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void loginInvalidCredentialsThrowsAuthorizationException() throws AuthorizationException {
        // Arrange
        String password = "wrongpass";

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(TEST_EMAIL, password);

        // Stubbing the service method to throw an exception
        Mockito.when(authorizationService.login(TEST_EMAIL, password)).thenThrow(AuthorizationException.class);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationController.login(authorizationDTO));
    }
}

