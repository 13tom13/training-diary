package exceptions;

/**
 * Класс ServiceException представляет исключение, которое выбрасывается при возникновении ошибок в сервисах.
 */
public class ServiceException extends Exception {

    /**
     * Конструктор класса ServiceException.
     * @param message Сообщение об ошибке.
     */
    public ServiceException(String message) {
        super("Ошибка сервиса в классе: " + message);
    }
}
