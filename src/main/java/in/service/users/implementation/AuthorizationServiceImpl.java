package in.service.users.implementation;

import entity.dto.UserDTO;
import entity.model.User;
import exceptions.security.AuthorizationException;
import exceptions.security.NotActiveUserException;
import in.repository.user.UserRepository;
import in.service.users.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import servlet.utils.annotations.Loggable;
import utils.mappers.UserMapper;

import java.util.Optional;

/**
 * Реализация сервиса аутентификации пользователей.
 */
@Loggable
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;

    /**
     * Конструктор для создания объекта класса AuthorizationServiceImpl.
     *
     * @param userRepository репозиторий пользователей
     */
    @Autowired
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
    public UserDTO login(String email, String password) throws AuthorizationException, NotActiveUserException {
        Optional<User> userFromDB = userRepository.getUserByEmail(email);
        if (userFromDB.isPresent()) {
            if (userFromDB.get().getPassword().equals(password)) {
                if (userFromDB.get().isActive()){
                    return UserMapper.INSTANCE.userToUserDTO(userFromDB.get());
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