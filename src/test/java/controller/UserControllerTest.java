package controller;

import in.controller.UserController;
import in.exception.RepositoryException;
import in.exception.ValidationException;
import in.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testutil.TestUtil;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


public class UserControllerTest extends TestUtil {

    @Mock
    private UserService userServiceMock;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userServiceMock);
    }


    @Test
    public void testCreateNewUser_Successful() throws ValidationException, RepositoryException {
        // Set up values for the test
        String firstName = "John";
        String lastName = "Doe";
        String password = "password";

        // Configure behavior of the userServiceMock object
        doNothing().when(userServiceMock).saveUser(firstName, lastName, TEST_EMAIL, password);

        // Call the method under test
        userController.createNewUser(firstName, lastName, TEST_EMAIL, password);

        // Verify if userServiceMock.saveUser is called with correct arguments
        verify(userServiceMock).saveUser(firstName, lastName, TEST_EMAIL, password);
    }
}