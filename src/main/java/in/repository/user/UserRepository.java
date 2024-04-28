package in.repository.user;

import exceptions.RepositoryException;
import entities.model.Rights;
import entities.model.User;

import java.sql.SQLException;
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

    List<Rights> getAllRights();

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
     * @param user объект пользователя, которого нужно удалить
     */
    void deleteUser(User user);

    /**
     * Присваивает пользователю список прав.
     *
     * @param user объект пользователя, которому присваиваются права
     * @throws SQLException если произошла ошибка при выполнении SQL-запроса
     */
    void assignUserRights(User user) throws SQLException;

    /**
     * Присваивает пользователю список ролей.
     *
     * @param user объект пользователя, которому присваиваются роли
     * @throws SQLException если произошла ошибка при выполнении SQL-запроса
     */
    void assignUserRoles(User user) throws SQLException;

}
