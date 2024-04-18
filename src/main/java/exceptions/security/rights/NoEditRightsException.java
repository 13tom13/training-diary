package exceptions.security.rights;

/**
 * Класс NoEditRightsException представляет исключение, которое выбрасывается при попытке редактирования без соответствующих прав доступа.
 */
public class NoEditRightsException extends NoRightsException {

    /**
     * Конструктор класса NoEditRightsException.
     */
    public NoEditRightsException() {
        super("изменения");
    }
}
