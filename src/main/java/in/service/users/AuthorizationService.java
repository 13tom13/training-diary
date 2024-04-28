package in.service.users;

import exceptions.security.AuthorizationException;
import entities.model.User;

/**
 * Сервис аутентификации пользователей.
 */
public interface AuthorizationService {

    /**
     * Вход пользователя в систему.
     *
     * @param email    электронная почта пользователя
     * @param password пароль пользователя
     * @return объект пользователя, если аутентификация прошла успешно
     * @throws AuthorizationException если аутентификация не удалась из-за неверных учетных данных
     */
    User login(String email, String password) throws AuthorizationException;
}
