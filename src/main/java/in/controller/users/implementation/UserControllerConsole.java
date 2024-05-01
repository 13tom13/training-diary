package in.controller.users.implementation;

import entities.dto.RegistrationDTO;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import in.controller.users.UserController;
import in.service.users.UserService;
import utils.Logger;

/**
 * Реализация интерфейса {@link UserController} для хранения тренировок.
 */
public class UserControllerConsole implements UserController {

    private final UserService userService;

    private final Logger logger = Logger.getInstance();

    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService Сервис пользователей
     */
    public UserControllerConsole(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает нового пользователя.
     * @param registrationDTO объект, содержащий данные нового пользователя
     */
    public void createNewUser(RegistrationDTO registrationDTO) {
        try {
            userService.saveUser(registrationDTO);
            logger.logAction(registrationDTO.getEmail(), "created");
        } catch (ValidationException | RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }
}
