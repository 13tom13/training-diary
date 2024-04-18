package exceptions;

import static utils.DateValidationService.DATE_FORMAT;

/**
 * Класс InvalidDateFormatException представляет исключение, которое выбрасывается при неверном формате даты.
 */
public class InvalidDateFormatException extends Exception {

    /**
     * Конструктор класса InvalidDateFormatException.
     */
    public InvalidDateFormatException() {
        super("Неверный формат даты. Пожалуйста, введите дату в формате " + DATE_FORMAT);
    }
}
