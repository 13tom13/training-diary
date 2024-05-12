package in.service.users;

import entity.dto.UserDTO;
import entity.model.Roles;
import exceptions.security.AuthorizationException;

import java.sql.SQLException;
import java.util.List;

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

    List<Roles> getUserRoles(Long userId) throws SQLException;
}
