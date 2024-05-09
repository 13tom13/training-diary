package in.controller.authorization.implementation;

import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import in.controller.authorization.AuthorizationController;
import exceptions.security.AuthorizationException;
import org.springframework.http.ResponseEntity;
import utils.Logger;
import in.service.users.AuthorizationService;


/**
 * Реализация интерфейса {@link AuthorizationController}.
 * Этот класс предоставляет методы для авторизации пользователей в системе.
 */
public class AuthorizationControllerConsole implements AuthorizationController {

    private final AuthorizationService authorizationService;
    private final Logger logger;

    /**
     * Конструктор для создания объекта контроллера авторизации.
     *
     * @param authorizationService Сервис авторизации.
     */
    public AuthorizationControllerConsole(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
        this.logger = Logger.getInstance();
    }

    /**
     * Метод для выполнения входа пользователя в систему.
     *
     * @param authorizationDTO@return Объект пользователя, если авторизация прошла успешно.
     * @throws AuthorizationException Если авторизация не удалась.
     */
    public ResponseEntity<?> login(AuthorizationDTO authorizationDTO) throws AuthorizationException {
        UserDTO userDTO = authorizationService.login(authorizationDTO.getEmail(), authorizationDTO.getPassword());
        if (userDTO != null)
            logger.logAction(authorizationDTO.getEmail(), "Вошел в систему");
        return userDTO;
    }
}
