package in.controller.authorization;

import entities.dto.AuthorizationDTO;
import exceptions.security.AuthorizationException;
import entities.model.User;

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
    User login(AuthorizationDTO authorizationDTO) throws AuthorizationException;
}
