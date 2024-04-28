package in.controller.users;

import entities.model.Rights;
import entities.model.User;

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
    User getUser(String email);

    /**
     * Изменяет имя пользователя.
     *
     * @param user     Пользователь, у которого будет изменено имя.
     * @param newName  Новое имя для пользователя.
     */
    void changeUserName(User user, String newName);

    /**
     * Изменяет фамилию пользователя.
     *
     * @param user        Пользователь, у которого будет изменена фамилия.
     * @param newLastName Новая фамилия пользователя.
     */
    void changeUserLastName(User user, String newLastName);

    /**
     * Изменяет пароль пользователя.
     *
     * @param user         Пользователь, у которого будет изменен пароль.
     * @param newPassword  Новый пароль для пользователя.
     */
    void changeUserPassword(User user, String newPassword);

    /**
     * Активирует или деактивирует пользователя.
     *
     * @param user  Пользователь, активационный статус которого будет изменен.
     */
    void changeUserActive(User user);

    /**
     * Изменяет права пользователя.
     *
     * @param user         Пользователь, права которого будут изменены.
     * @param userRights   Новые права для пользователя.
     */
    void changeUserRights(User user, List<Rights> userRights);

    /**
     * Удаляет пользователя.
     *
     * @param user  Пользователь, который будет удален.
     */
    void deleteUser(User user);

    List<Rights> getAllRights();
}
