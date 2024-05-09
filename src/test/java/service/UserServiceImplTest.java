package service;

import entity.dto.RegistrationDTO;
import exceptions.ServiceException;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

//
//    @Test
//    public void testGetUserByEmail_WhenUserNotFound() {
//
//        // Act & Assert
//        assertThatThrownBy(() -> userService.getUserByEmail(TEST_EMAIL))
//                .isInstanceOf(ServiceException.class); // Проверка на ошибку сервиса
//    }
//
//
//    @Test
//    public void testSaveUser_WithValidData() {
//        // Act & Assert
//        RegistrationDTO registrationDTO = new RegistrationDTO(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
//        assertThatCode(() -> userService.saveUser(registrationDTO))
//                .doesNotThrowAnyException();
//    }



}
