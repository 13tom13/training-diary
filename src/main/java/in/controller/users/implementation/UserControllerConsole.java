package in.controller.users.implementation;

import entity.dto.RegistrationDTO;
import exceptions.RepositoryException;
import in.controller.users.UserController;
import in.service.users.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import utils.Logger;

/**
 * Реализация интерфейса {@link UserController} для хранения тренировок.
 */
@Controller
@RequiredArgsConstructor
public class UserControllerConsole implements UserController {

    private final UserService userService;

    private final Logger logger = Logger.getInstance();

    /**
     * Создает нового пользователя.
     *
     * @param registrationDTO объект, содержащий данные нового пользователя
     * @return
     */
    public ResponseEntity<String> createNewUser(RegistrationDTO registrationDTO) {
        try {
            var validate = validator.validate(registrationDTO);
            if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
            userService.saveUser(registrationDTO);
            logger.logAction(registrationDTO.getEmail(), "created");
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
