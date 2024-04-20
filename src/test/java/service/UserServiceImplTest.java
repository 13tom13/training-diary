package service;

import exceptions.ServiceException;
import exceptions.ValidationException;
import in.repository.user.UserRepository;
import in.service.users.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testutil.TestUtil.*;

/**
 * Тестовый класс для {@link UserServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    /**
     * Тест для метода {@link UserServiceImpl#getUserByEmail(String)}.
     * Проверяет сценарий, когда пользователь не найден.
     */
    @Test
    public void testGetUserByEmail_WhenUserNotFound() {

        // Act & Assert
        assertThatThrownBy(() -> userService.getUserByEmail(TEST_EMAIL))
                .isInstanceOf(ServiceException.class); // Проверка на ошибку сервиса
    }

    /**
     * Тест для метода {@link UserServiceImpl#saveUser(String, String, String, String)}.
     * Проверяет сценарий, когда все данные пользователя валидны.
     */
    @Test
    public void testSaveUser_WithValidData() {
        // Act & Assert
        assertThatCode(() -> userService.saveUser(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD))
                .doesNotThrowAnyException();
    }

    /**
     * Тест для метода {@link UserServiceImpl#saveUser(String, String, String, String)}.
     * Проверяет сценарий, когда передано null в качестве имени пользователя.
     */
    @Test
    public void testSaveUser_WithNullFirstName() {
        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.saveUser(null, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD));
    }

}
