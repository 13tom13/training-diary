package controller;

import dto.UserDTO;
import in.controller.users.implementation.UserControllerImpl;
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
public class UserControllerImplTest extends TestUtil {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserControllerImpl userController;

    @Test
    public void testCreateNewUser_Successful() throws ValidationException, RepositoryException {
        // Set up values for the test
        UserDTO userDTO = new UserDTO(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);

        // Configure behavior of the userServiceMock object
        doNothing().when(userServiceMock).saveUser(userDTO);

        // Call the method under test
        userController.createNewUser(userDTO);

        // Verify if userServiceMock.saveUser is called with correct arguments
        verify(userServiceMock).saveUser(userDTO);

        // Assert that no exceptions were thrown during the execution
        assertThatCode(() -> userController.createNewUser(userDTO))
                .doesNotThrowAnyException();
    }
}
