package in.service.users;

import exceptions.RepositoryException;
import exceptions.ServiceException;
import exceptions.ValidationException;
import model.User;

/**
 * Интерфейс сервиса для работы с пользователями.
 */
public interface UserService {

    /**
     * Получает пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект пользователя
     * @throws ServiceException если произошла ошибка при получении пользователя
     */
    User getUserByEmail(String email) throws ServiceException;

    /**
     * Сохраняет нового пользователя.
     *
     * @param firstName имя пользователя
     * @param lastName  фамилия пользователя
     * @param email     электронная почта пользователя
     * @param password  пароль пользователя
     * @throws RepositoryException если произошла ошибка доступа к хранилищу
     * @throws ValidationException если произошла ошибка валидации данных пользователя
     */
    void saveUser(String firstName, String lastName, String email, String password) throws RepositoryException, ValidationException;

}