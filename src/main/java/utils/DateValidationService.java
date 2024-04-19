package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Сервис для проверки формата даты.
 */
public class DateValidationService {
    /**
     * Конструктор класса DateValidationService.
     * Приватный конструктор, поскольку все методы в этом классе являются статическими.
     */
    private DateValidationService() {
        // Приватный конструктор, чтобы предотвратить создание экземпляров этого класса.
    }


    /**
     * Формат даты.
     */
    public static final String DATE_FORMAT = "dd.MM.yy";

    /**
     * Проверяет, является ли переданная строка допустимым форматом даты.
     *
     * @param date Строка, предположительно содержащая дату
     * @return {@code true}, если строка соответствует формату даты, в противном случае {@code false}
     */
    public static boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
