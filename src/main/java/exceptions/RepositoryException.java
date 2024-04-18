package exceptions;

/**
 * Класс RepositoryException представляет исключение, которое выбрасывается при ошибке в репозиториях.
 */
public class RepositoryException extends Exception {

    /**
     * Конструктор класса RepositoryException.
     * @param message Сообщение об ошибке.
     */
    public RepositoryException(String message) {
        super(message);
    }
}
