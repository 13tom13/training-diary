package in.repository.user.implementation;

import exceptions.RepositoryException;
import model.Rights;
import model.User;
import in.repository.user.UserRepository;

import java.util.*;

/**
 * Реализация интерфейса {@link UserRepository}, предоставляющая методы для работы с хранилищем пользователей.
 */
public class UserRepositoryCollections implements UserRepository {

    /** Хранилище пользователей, где ключами являются адреса электронной почты пользователей. */
    private final Map<String, User> userRepository;

    /**
     * Конструктор по умолчанию, инициализирующий хранилище пользователей.
     */
    public UserRepositoryCollections() {
        userRepository = new HashMap<>();
    }

    /**
     * Получает пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект Optional, содержащий пользователя, если пользователь найден, иначе пустой объект
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        User user = userRepository.get(email);
        return Optional.ofNullable(user);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public List<Rights> getAllRights() {
        return null;
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param user объект пользователя для сохранения
     * @throws RepositoryException если пользователь с такой электронной почтой уже существует
     */
    @Override
    public void saveUser(User user) throws RepositoryException {
        if (!userRepository.containsKey(user.getEmail())) {
            userRepository.put(user.getEmail(), user);
        } else {
            throw new RepositoryException("Пользователь с адресом электронной почты " + user.getEmail() + " уже существует");
        }
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param user объект пользователя для обновления
     */
    @Override
    public void updateUser(User user) {
        userRepository.put(user.getEmail(), user);
    }

    @Override
    public void deleteUser(User user) {
    }

}
