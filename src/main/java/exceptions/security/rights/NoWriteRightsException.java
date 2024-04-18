package exceptions.security.rights;

/**
 * Класс NoWriteRightsException представляет исключение, которое выбрасывается при попытке записи без соответствующих прав доступа.
 */
public class NoWriteRightsException extends NoRightsException{

    /**
     * Конструктор класса NoWriteRightsException.
     */
    public NoWriteRightsException() {
        super("для записи");
    }
}
