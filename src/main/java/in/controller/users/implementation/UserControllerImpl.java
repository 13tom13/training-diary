package in.controller.users.implementation;

import dto.UserDTO;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import in.controller.users.UserController;
import in.service.users.UserService;
import utils.Logger;

/**
 * Реализация интерфейса {@link UserController} для хранения тренировок.
 */
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final Logger logger = Logger.getInstance();

    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService Сервис пользователей
     */
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает нового пользователя.
     *
     * @param userDTO объект, содержащий данные нового пользователя
     */
    public void createNewUser(UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            logger.logAction(userDTO.getEmail(), "created");
        } catch (ValidationException | RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }
}
