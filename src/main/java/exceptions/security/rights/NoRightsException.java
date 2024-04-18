package exceptions.security.rights;

/**
 * Абстрактный класс NoRightsException представляет исключение, которое выбрасывается при отсутствии прав доступа.
 */
public abstract class NoRightsException extends Exception{

    /**
     * Конструктор класса NoRightsException.
     * @param message Сообщение об ошибке.
     */
    public NoRightsException(String message) {
        super("У вас нет прав для " + message);
    }
}
