package in.controller.authorization.implementation;

import entities.dto.AuthorizationDTO;
import in.controller.authorization.AuthorizationController;
import exceptions.security.AuthorizationException;
import utils.Logger;
import entities.model.User;
import in.service.users.AuthorizationService;


/**
 * Реализация интерфейса {@link AuthorizationController}.
 * Этот класс предоставляет методы для авторизации пользователей в системе.
 */
public class AuthorizationControllerImpl implements AuthorizationController {

    private final AuthorizationService authorizationService;
    private final Logger logger;

    /**
     * Конструктор для создания объекта контроллера авторизации.
     *
     * @param authorizationService Сервис авторизации.
     */
    public AuthorizationControllerImpl(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
        this.logger = Logger.getInstance();
    }

    /**
     * Метод для выполнения входа пользователя в систему.
     *
     * @param authorizationDTO@return Объект пользователя, если авторизация прошла успешно.
     * @throws AuthorizationException Если авторизация не удалась.
     */
    public User login(AuthorizationDTO authorizationDTO) throws AuthorizationException {
        User user = authorizationService.login(authorizationDTO.getEmail(), authorizationDTO.getPassword());
        logger.logAction(authorizationDTO.getEmail(), "Вошел в систему");
        return user;
    }
}
