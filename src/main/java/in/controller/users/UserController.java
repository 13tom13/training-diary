package in.controller.users;

import entities.dto.RegistrationDTO;

import java.io.IOException;

/**
 * Интерфейс контроллера пользователей.
 */
public interface UserController {

    /**
     * Создает нового пользователя с заданными данными.
     *
     * @param registrationDTO
     */
    void createNewUser(RegistrationDTO registrationDTO) throws IOException;
}

