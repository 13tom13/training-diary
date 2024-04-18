package controller;

import in.controller.users.implementation.UserControllerImpl;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import in.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import testutil.TestUtil;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * Тестирование класса UserControllerImpl.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerImplTest extends TestUtil {

    @Mock
    private UserService userServiceMock;

    private UserControllerImpl userController;

    @BeforeEach
    public void setUp() {
        userController = new UserControllerImpl(userServiceMock);
    }

    /**
     * Тестирование успешного создания нового пользователя.
     *
     * @throws ValidationException если возникли проблемы с валидацией данных пользователя
     * @throws RepositoryException если возникли проблемы с доступом к репозиторию пользователей
     */
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
