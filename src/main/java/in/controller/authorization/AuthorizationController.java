package in.controller.authorization;

import entities.dto.AuthorizationDTO;
import entities.dto.UserDTO;
import exceptions.security.AuthorizationException;

/**
 * Интерфейс контроллера авторизации.
 */
public interface AuthorizationController {

    /**
     * Метод для входа пользователя в систему.
     *
     * @param authorizationDTO@return объект пользователя, который вошел в систему
     * @throws AuthorizationException если произошла ошибка авторизации
     */
    UserDTO login(AuthorizationDTO authorizationDTO) throws AuthorizationException;
}
