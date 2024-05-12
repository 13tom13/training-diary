package in.service.users;

import entity.dto.RegistrationDTO;
import entity.model.User;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import jakarta.validation.Validator;
import utils.ValidatorFactoryProvider;

/**
 * Интерфейс сервиса для работы с пользователями.
 */
public interface UserService {

    Validator validator = ValidatorFactoryProvider.getValidator();

    /**
     * Получает пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект пользователя
     * @throws ServiceException    если произошла ошибка при получении пользователя
     * @throws RepositoryException если произошла ошибка доступа к хранилищу
     */
    User getUserByEmail(String email) throws ServiceException, RepositoryException;

    /**
     * Сохраняет нового пользователя.
     *
     * @param registrationDTO@throws RepositoryException если произошла ошибка доступа к хранилищу
     */
    void saveUser(RegistrationDTO registrationDTO) throws RepositoryException;

}
