package controller;

import entities.dto.RegistrationDTO;
import in.controller.users.implementation.UserControllerConsole;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import in.service.users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerConsoleTest extends TestUtil {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserControllerConsole userController;

    @Test
    public void testCreateNewUser_Successful() throws ValidationException, RepositoryException {

        RegistrationDTO registrationDTO = new RegistrationDTO(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        doNothing().when(userServiceMock).saveUser(registrationDTO);


        userController.createNewUser(registrationDTO);


        verify(userServiceMock).saveUser(registrationDTO);

        // Assert that no exceptions were thrown during the execution
        assertThatCode(() -> userController.createNewUser(registrationDTO))
                .doesNotThrowAnyException();
    }
}
