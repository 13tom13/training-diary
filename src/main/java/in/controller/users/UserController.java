package in.controller.users;

import entities.dto.RegistrationDTO;
import jakarta.validation.Validator;
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
     */
    void createNewUser(RegistrationDTO registrationDTO) throws IOException;
}

