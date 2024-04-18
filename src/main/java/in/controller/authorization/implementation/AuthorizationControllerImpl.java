package in.controller.authorization.implementation;

import in.controller.authorization.AuthorizationController;
import exceptions.security.AuthorizationException;
import utils.Logger;
import model.User;
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
     * @param email    Электронная почта пользователя.
     * @param password Пароль пользователя.
     * @return Объект пользователя, если авторизация прошла успешно.
     * @throws AuthorizationException Если авторизация не удалась.
     */
    public User login(String email, String password) throws AuthorizationException {
        User user = authorizationService.login(email, password);
        logger.logAction(email, "Вошел в систему");
        return user;
    }
}
