package in.controller.users;

import dto.UserDTO;

/**
 * Интерфейс контроллера пользователей.
 */
public interface UserController {

    /**
     * Создает нового пользователя с заданными данными.
     *
     * @param dto объект, содержащий данные нового пользователя
     */
    void createNewUser(UserDTO dto);
}

