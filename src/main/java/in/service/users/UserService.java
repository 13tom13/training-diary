package in.service.users;

import dto.UserDTO;
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
     * @throws RepositoryException если произошла ошибка доступа к хранилищу
     */
    User getUserByEmail(String email) throws ServiceException, RepositoryException;

    /**
     * Сохраняет нового пользователя.
     *
     * @param userDTO объект, содержащий данные нового пользователя
     * @throws RepositoryException если произошла ошибка доступа к хранилищу
     * @throws ValidationException если произошла ошибка валидации данных пользователя
     */
    void saveUser(UserDTO userDTO) throws RepositoryException, ValidationException;

}
