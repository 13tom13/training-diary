package exceptions.security.rights;

/**
 * Класс NoStatisticsRightsException представляет исключение, которое выбрасывается при попытке просмотра статистики без соответствующих прав доступа.
 */
public class NoStatisticsRightsException extends NoRightsException {

    /**
     * Конструктор класса NoStatisticsRightsException.
     */
    public NoStatisticsRightsException() {
        super("просмотра статистики");
    }
}
