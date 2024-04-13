package in.exception;

import static in.service.implementation.DateValidationService.DATE_FORMAT;

public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException() {
        super("Неверный формат даты. Пожалуйста, введите дату в формате" + DATE_FORMAT);
    }
}
