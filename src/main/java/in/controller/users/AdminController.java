package in.controller.users;

import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.User;
import exceptions.RepositoryException;
import exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResponseEntity<List<User>> getAllUsers();

    /**
     * Получает пользователя по его адресу электронной почты.
     *
     * @param email Адрес электронной почты пользователя для поиска.
     * @return Пользователь с указанным адресом электронной почты или null, если не найден.
     */
    ResponseEntity<UserDTO> getUser(String email) throws UserNotFoundException, IOException;

    /**
     * Изменяет имя пользователя.
     *
     * @param userDTO Пользователь, у которого будет изменено имя.
     * @param newName Новое имя для пользователя.
     */
    ResponseEntity<UserDTO> changeUserName(UserDTO userDTO, String newName) throws RepositoryException;

    /**
     * Изменяет фамилию пользователя.
     *
     * @param userDTO     Пользователь, у которого будет изменена фамилия.
     * @param newLastName Новая фамилия пользователя.
     */
    ResponseEntity<UserDTO> changeUserLastName(UserDTO userDTO, String newLastName) throws RepositoryException;

    /**
     * Изменяет пароль пользователя.
     *
     * @param userDTO     Пользователь, у которого будет изменен пароль.
     * @param newPassword Новый пароль для пользователя.
     */
    ResponseEntity<UserDTO> changeUserPassword(UserDTO userDTO, String newPassword) throws RepositoryException;

    /**
     * Активирует или деактивирует пользователя.
     *
     * @param userDTO Пользователь, активационный статус которого будет изменен.
     */
    ResponseEntity<UserDTO> changeUserActive(UserDTO userDTO) throws RepositoryException;

    /**
     * Изменяет права пользователя.
     *
     * @param userDTO    Пользователь, права которого будут изменены.
     * @param userRights Новые права для пользователя.
     */
    ResponseEntity<UserDTO> changeUserRights(UserDTO userDTO, List<Rights> userRights) throws RepositoryException;

    /**
     * Удаляет пользователя.
     *
     * @param email Пользователь, который будет удален.
     * @return
     */
    ResponseEntity<Void> deleteUser(String email);

    @GetMapping("/user/rights")
    ResponseEntity<List<Rights>> getUserRights(@RequestParam("user") long id) throws IOException;

    ResponseEntity<List<Rights>> getAllRights() throws IOException;

    @GetMapping("/getUserRoles")
    ResponseEntity<?> login(@RequestParam("userId") Long userId);
}
