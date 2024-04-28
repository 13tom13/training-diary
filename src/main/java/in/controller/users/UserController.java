package in.controller.users;

import entities.dto.RegistrationDTO;

/**
 * Интерфейс контроллера пользователей.
 */
public interface UserController {

    /**
     * Создает нового пользователя с заданными данными.
     *
     * @param registrationDTO
     */
    void createNewUser(RegistrationDTO registrationDTO);
}

