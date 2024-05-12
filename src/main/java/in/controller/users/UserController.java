package in.controller.users;

import entity.dto.RegistrationDTO;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import utils.ValidatorFactoryProvider;

import java.io.IOException;

/**
 * Интерфейс контроллера пользователей.
 */
public interface UserController {

    Validator validator = ValidatorFactoryProvider.getValidator();

    /**
     * Создает нового пользователя с заданными данными.
     *
     * @param registrationDTO
     * @return
     */
    ResponseEntity<String> createNewUser(RegistrationDTO registrationDTO) throws IOException;
}

