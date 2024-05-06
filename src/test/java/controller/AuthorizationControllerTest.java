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
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest extends TestUtil {

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private AuthorizationControllerConsole authorizationController;

    @Test
    void loginValidCredentialsReturnsUser() throws AuthorizationException {
        // Arrange
        UserDTO expectedUser =  createTestUserDTO();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(TEST_EMAIL, TEST_PASSWORD);

        // Stubbing the service method
        when(authorizationService.login(TEST_EMAIL, TEST_PASSWORD)).thenReturn(expectedUser);

        // Act
        UserDTO actualUser = authorizationController.login(authorizationDTO);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void loginInvalidCredentialsThrowsAuthorizationException() throws AuthorizationException {
        // Arrange
        String wrongPassword = "wrongpass";

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(TEST_EMAIL, wrongPassword);

        // Stubbing the service method to throw an exception
        when(authorizationService.login(TEST_EMAIL, wrongPassword)).thenThrow(AuthorizationException.class);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> authorizationController.login(authorizationDTO));
    }
}
