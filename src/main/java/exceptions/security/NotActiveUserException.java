package exceptions.security;

/**
 * Класс NotActiveUserException представляет исключение, которое выбрасывается при попытке входа в систему неактивного пользователя.
 */
public class NotActiveUserException extends AuthorizationException {

    /**
     * Конструктор класса NotActiveUserException.
     */
    public NotActiveUserException() {
        super("Ваш аккаунт не активен");
    }
}
