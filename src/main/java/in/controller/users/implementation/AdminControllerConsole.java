package in.controller.users.implementation;

import entities.dto.UserDTO;
import entities.model.User;
import exceptions.UserNotFoundException;
import in.controller.users.AdminController;
import entities.model.Rights;
import in.repository.user.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import utils.ValidatorFactoryProvider;
import utils.mappers.UserMapper;


import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link AdminController}.
 * Этот класс предоставляет методы для администрирования пользователей.
 */
public class AdminControllerConsole implements AdminController {

    private final UserRepository userRepository;
    private final Validator validator = ValidatorFactoryProvider.getValidator();


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
    public UserDTO changeUserName(UserDTO userDTO, String newName) {
        var validate = validator.validate(userDTO);
        if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setFirstName(newName);
        return updateAndGetUser(user);
    }

    /**
     * Изменяет фамилию пользователя.
     *
     * @param userDTO        объект пользователя
     * @param newLastName новая фамилия пользователя
     */
    @Override
    public UserDTO changeUserLastName(UserDTO userDTO, String newLastName) {
        var validate = validator.validate(userDTO);
        if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setLastName(newLastName);
        return updateAndGetUser(user);
    }

    /**
     * Изменяет пароль пользователя.
     *
     * @param userDTO        объект пользователя
     * @param newPassword новый пароль пользователя
     */
    @Override
    public UserDTO changeUserPassword(UserDTO userDTO, String newPassword) {
        var validate = validator.validate(userDTO);
        if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setPassword(newPassword);
        return updateAndGetUser(user);
    }

    /**
     * Изменяет активность пользователя.
     *
     * @param userDTO объект пользователя
     */
    @Override
    public UserDTO changeUserActive(UserDTO userDTO) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setActive(!userDTO.isActive());
        return updateAndGetUser(user);
    }

    /**
     * Изменяет права пользователя.
     *
     * @param userDTO       объект пользователя
     * @param userRights новые права пользователя
     */
    @Override
    public UserDTO changeUserRights(UserDTO userDTO, List<Rights> userRights) {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        user.setRights(userRights);
        return updateAndGetUser(user);
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

    private UserDTO updateAndGetUser(User user) {
        userRepository.updateUser(user);
        User updateUser = userRepository.getUserByEmail(user.getEmail()).get();
        return UserMapper.INSTANCE.userToUserDTO(updateUser);
    }
}
