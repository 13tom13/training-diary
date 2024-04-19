package controller;

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

/**
 * Тестирование класса UserControllerImpl.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerImplTest extends TestUtil {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserControllerImpl userController;

    /**
     * Тестирование успешного создания нового пользователя.
     *
     * @throws ValidationException если возникли проблемы с валидацией данных пользователя
     * @throws RepositoryException если возникли проблемы с доступом к репозиторию пользователей
     */
    @Test
    public void testCreateNewUser_Successful() throws ValidationException, RepositoryException {
        // Set up values for the test
        String firstName = TEST_FIRST_NAME;
        String lastName = TEST_LAST_NAME;
        String password = TEST_PASSWORD;

        // Configure behavior of the userServiceMock object
        doNothing().when(userServiceMock).saveUser(firstName, lastName, TEST_EMAIL, password);

        // Call the method under test
        userController.createNewUser(firstName, lastName, TEST_EMAIL, password);

        // Verify if userServiceMock.saveUser is called with correct arguments
        verify(userServiceMock).saveUser(firstName, lastName, TEST_EMAIL, password);

        // Assert that no exceptions were thrown during the execution
        assertThatCode(() -> userController.createNewUser(firstName, lastName, TEST_EMAIL, password))
                .doesNotThrowAnyException();
    }
}
