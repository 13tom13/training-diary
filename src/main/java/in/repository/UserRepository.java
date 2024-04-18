package in.repository;

import exceptions.RepositoryException;
import model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с хранилищем пользователей.
 */
public interface UserRepository {

    /**
     * Получить пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект Optional, содержащий пользователя, если такой найден, или пустой объект, если пользователь не найден
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Получить всех пользователей из хранилища.
     *
     * @return список всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Сохранить пользователя в хранилище.
     *
     * @param user объект пользователя для сохранения
     * @throws RepositoryException если пользователь с такой электронной почтой уже существует
     */
    void saveUser(User user) throws RepositoryException;

    /**
     * Обновить информацию о пользователе в хранилище.
     *
     * @param user объект пользователя с обновленной информацией
     */
    void updateUser(User user);

    /**
     * Удалить пользователя из хранилища по его электронной почте.
     *
     * @param email электронная почта пользователя, которого нужно удалить
     */
    void deleteUser(String email);

}
