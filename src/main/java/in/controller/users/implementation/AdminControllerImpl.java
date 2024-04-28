package in.controller.users.implementation;

import in.controller.users.AdminController;
import entities.model.Rights;
import entities.model.User;
import in.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link AdminController}.
 * Этот класс предоставляет методы для администрирования пользователей.
 */
public class AdminControllerImpl implements AdminController {

    private final UserRepository userRepository;

    /**
     * Конструктор класса AdminControllerImpl.
     *
     * @param userRepository репозиторий пользователей, используемый этим контроллером.
     */
    public AdminControllerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /**
     * Получает пользователя по его адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return объект пользователя, если он существует, в противном случае null
     */
    @Override
    public User getUser(String email) {
        Optional<User> userOptional = userRepository.getUserByEmail(email);
        return userOptional.orElse(null);
    }

    /**
     * Изменяет имя пользователя.
     *
     * @param user    объект пользователя
     * @param newName новое имя пользователя
     */
    @Override
    public void changeUserName(User user, String newName) {
        user.setFirstName(newName);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет фамилию пользователя.
     *
     * @param user        объект пользователя
     * @param newLastName новая фамилия пользователя
     */
    @Override
    public void changeUserLastName(User user, String newLastName) {
        user.setLastName(newLastName);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет пароль пользователя.
     *
     * @param user        объект пользователя
     * @param newPassword новый пароль пользователя
     */
    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет активность пользователя.
     *
     * @param user объект пользователя
     */
    @Override
    public void changeUserActive(User user) {
        user.setActive(!user.isActive());
        userRepository.updateUser(user);
    }

    /**
     * Изменяет права пользователя.
     *
     * @param user       объект пользователя
     * @param userRights новые права пользователя
     */
    @Override
    public void changeUserRights(User user, List<Rights> userRights) {
        user.setRights(userRights);
        userRepository.updateUser(user);
    }

    /**
     * Удаляет пользователя.
     *
     * @param user объект пользователя для удаления
     */
    @Override
    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    @Override
    public List<Rights> getAllRights() {
        return userRepository.getAllRights();
    }
}
