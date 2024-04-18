package exceptions.security;

/**
 * Класс AuthorizationException представляет исключение, которое выбрасывается при возникновении ошибок авторизации.
 */
public class AuthorizationException extends Exception {

    /**
     * Конструктор класса AuthorizationException.
     * @param message Сообщение об ошибке.
     */
    public AuthorizationException(String message) {
        super(message);
    }
}
