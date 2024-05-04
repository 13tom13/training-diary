package in.controller.users.implementation;

import entities.dto.UserDTO;
import entities.model.User;
import exceptions.UserNotFoundException;
import in.controller.users.AdminController;
import entities.model.Rights;
import in.repository.user.UserRepository;
import servlet.mappers.UserMapper;


import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link AdminController}.
 * Этот класс предоставляет методы для администрирования пользователей.
 */
public class AdminControllerConsole implements AdminController {

    private final UserRepository userRepository;

    /**
     * Конструктор класса AdminControllerConsole.
     *
     * @param userRepository репозиторий пользователей, используемый этим контроллером.
     */
    public AdminControllerConsole(UserRepository userRepository) {
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
    public UserDTO getUser(String email) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.getUserByEmail(email);
        // Проверка, найден ли пользователь
        if (userOptional.isPresent()) {
            // Если пользователь найден, мапим его в UserDTO и возвращаем
            return UserMapper.INSTANCE.userToUserDTO(userOptional.get());
        } else {
            // Если пользователь не найден, выбрасываем исключение UserNotFoundException
            throw new UserNotFoundException(email);
        }
    }

    /**
     * Изменяет имя пользователя.
     *
     * @param userDTO    объект пользователя
     * @param newName новое имя пользователя
     */
    @Override
    public void changeUserName(UserDTO userDTO, String newName) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setFirstName(newName);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет фамилию пользователя.
     *
     * @param userDTO        объект пользователя
     * @param newLastName новая фамилия пользователя
     */
    @Override
    public void changeUserLastName(UserDTO userDTO, String newLastName) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setLastName(newLastName);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет пароль пользователя.
     *
     * @param userDTO        объект пользователя
     * @param newPassword новый пароль пользователя
     */
    @Override
    public void changeUserPassword(UserDTO userDTO, String newPassword) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setPassword(newPassword);
        userRepository.updateUser(user);
    }

    /**
     * Изменяет активность пользователя.
     *
     * @param userDTO объект пользователя
     */
    @Override
    public void changeUserActive(UserDTO userDTO) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setActive(!userDTO.isActive());
        userRepository.updateUser(user);
    }

    /**
     * Изменяет права пользователя.
     *
     * @param userDTO       объект пользователя
     * @param userRights новые права пользователя
     */
    @Override
    public void changeUserRights(UserDTO userDTO, List<Rights> userRights) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setRights(userRights);
        userRepository.updateUser(user);
    }

    /**
     * Удаляет пользователя.
     *
     * @param userDTO объект пользователя для удаления
     */
    @Override
    public void deleteUser(UserDTO userDTO) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        userRepository.deleteUser(user);
    }

    @Override
    public List<Rights> getAllRights() {
        return userRepository.getAllRights();
    }
}
