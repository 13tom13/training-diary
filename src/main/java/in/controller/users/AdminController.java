package in.controller.users;

import entities.dto.UserDTO;
import entities.model.Rights;
import entities.model.User;
import exceptions.UserNotFoundException;

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
    UserDTO getUser(String email) throws UserNotFoundException;

    /**
     * Изменяет имя пользователя.
     *
     * @param userDTO     Пользователь, у которого будет изменено имя.
     * @param newName  Новое имя для пользователя.
     */
    void changeUserName(UserDTO userDTO, String newName);

    /**
     * Изменяет фамилию пользователя.
     *
     * @param userDTO        Пользователь, у которого будет изменена фамилия.
     * @param newLastName Новая фамилия пользователя.
     */
    void changeUserLastName(UserDTO userDTO, String newLastName);

    /**
     * Изменяет пароль пользователя.
     *
     * @param userDTO         Пользователь, у которого будет изменен пароль.
     * @param newPassword  Новый пароль для пользователя.
     */
    void changeUserPassword(UserDTO userDTO, String newPassword);

    /**
     * Активирует или деактивирует пользователя.
     *
     * @param userDTO  Пользователь, активационный статус которого будет изменен.
     */
    void changeUserActive(UserDTO userDTO);

    /**
     * Изменяет права пользователя.
     *
     * @param userDTO         Пользователь, права которого будут изменены.
     * @param userRights   Новые права для пользователя.
     */
    void changeUserRights(UserDTO userDTO, List<Rights> userRights);

    /**
     * Удаляет пользователя.
     *
     * @param userDTO  Пользователь, который будет удален.
     */
    void deleteUser(UserDTO userDTO);

    List<Rights> getAllRights();
}
