package in.service.users;

import entities.dto.UserDTO;
import entities.model.User;
import exceptions.security.AuthorizationException;

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
    UserDTO login(String email, String password) throws AuthorizationException;
}
