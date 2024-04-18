package exceptions.security.rights;

/**
 * Класс NoDeleteRightsException представляет исключение, которое выбрасывается при попытке удаления без соответствующих прав доступа.
 */
public class NoDeleteRightsException extends NoRightsException {

    /**
     * Конструктор класса NoDeleteRightsException.
     */
    public NoDeleteRightsException() {
        super("удаления");
    }
}
