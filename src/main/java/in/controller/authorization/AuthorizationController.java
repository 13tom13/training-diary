package in.controller.authorization;

import exceptions.security.AuthorizationException;
import model.User;

/**
 * Интерфейс контроллера авторизации.
 */
public interface AuthorizationController {

    /**
     * Метод для входа пользователя в систему.
     *
     * @param email    адрес электронной почты пользователя
     * @param password пароль пользователя
     * @return объект пользователя, который вошел в систему
     * @throws AuthorizationException если произошла ошибка авторизации
     */
    User login(String email, String password) throws AuthorizationException;
}
