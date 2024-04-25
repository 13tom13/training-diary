package in.service.users.implementation;

import exceptions.security.AuthorizationException;
import exceptions.security.NotActiveUserException;
import model.User;
import in.repository.user.UserRepository;
import in.service.users.AuthorizationService;

import java.util.Optional;

/**
 * Реализация сервиса аутентификации пользователей.
 */
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;

    /**
     * Конструктор для создания объекта класса AuthorizationServiceImpl.
     *
     * @param userRepository репозиторий пользователей
     */
    public AuthorizationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Вход пользователя в систему.
     *
     * @param email    электронная почта пользователя
     * @param password пароль пользователя
     * @return объект пользователя, если аутентификация прошла успешно
     * @throws AuthorizationException    если аутентификация не удалась из-за неверных учетных данных
     * @throws NotActiveUserException    если пользователь не активен
     */
    @Override
    public User login(String email, String password) throws AuthorizationException, NotActiveUserException {
        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                if (user.get().isActive()){
                    return user.get();
                } else {
                    throw new NotActiveUserException();
                }
            } else {
                throw new AuthorizationException("Пароль не верный");
            }
        } else {
            throw new AuthorizationException("Пользователь не найден");
        }
    }
}