package in.controller.users;

import entities.dto.UserDTO;
import entities.model.Rights;
import entities.model.User;
import exceptions.RepositoryException;
import exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * Этот интерфейс представляет функциональность, предоставляемую контроллером администратора.
 */
public interface AdminController {

    /**
     * Получает всех пользователей.
     *
     * @return Список всех пользователей.
     */
    List<User> getAllUsers();

    /**
     * Получает пользователя по его адресу электронной почты.
     *
     * @param email Адрес электронной почты пользователя для поиска.
     * @return Пользователь с указанным адресом электронной почты или null, если не найден.
     */
    UserDTO getUser(String email) throws UserNotFoundException, IOException;

    /**
     * Изменяет имя пользователя.
     *
     * @param userDTO Пользователь, у которого будет изменено имя.
     * @param newName Новое имя для пользователя.
     */
    UserDTO changeUserName(UserDTO userDTO, String newName) throws RepositoryException;

    /**
     * Изменяет фамилию пользователя.
     *
     * @param userDTO     Пользователь, у которого будет изменена фамилия.
     * @param newLastName Новая фамилия пользователя.
     */
    UserDTO changeUserLastName(UserDTO userDTO, String newLastName) throws RepositoryException;

    /**
     * Изменяет пароль пользователя.
     *
     * @param userDTO     Пользователь, у которого будет изменен пароль.
     * @param newPassword Новый пароль для пользователя.
     */
    UserDTO changeUserPassword(UserDTO userDTO, String newPassword) throws RepositoryException;

    /**
     * Активирует или деактивирует пользователя.
     *
     * @param userDTO Пользователь, активационный статус которого будет изменен.
     */
    UserDTO changeUserActive(UserDTO userDTO) throws RepositoryException;

    /**
     * Изменяет права пользователя.
     *
     * @param userDTO    Пользователь, права которого будут изменены.
     * @param userRights Новые права для пользователя.
     */
    UserDTO changeUserRights(UserDTO userDTO, List<Rights> userRights) throws RepositoryException;

    /**
     * Удаляет пользователя.
     *
     * @param userDTO Пользователь, который будет удален.
     */
    void deleteUser(UserDTO userDTO);

    List<Rights> getAllRights() throws IOException;
}
