package in.exception.security.rights;

public class NoStatisticsRightsException extends NoRightsException {
    public NoStatisticsRightsException() {
        super("просмотра статистики");
    }
}
