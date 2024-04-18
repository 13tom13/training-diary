package exceptions;

/**
 * Класс ValidationException представляет исключение, которое выбрасывается при невалидации данных.
 */
public class ValidationException extends Exception {

    /**
     * Конструктор класса ValidationException.
     * @param field Название поля, которое не прошло валидацию.
     */
    public ValidationException(String field) {
        super("Поле \"" + field + "\" не может быть пустым");
    }
}
