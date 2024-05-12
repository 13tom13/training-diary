package utils;

import entity.model.Rights;
import entity.model.Roles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils {
    public static final String DATE_FORMAT = "dd.MM.yy";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);


    public static LocalDate getDateFromString(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, formatter);
    }


    public static String getStringFromDate(LocalDate date) {
        return formatter.format(date);
    }

    public static boolean hisRight(List<Rights> rights, String rightsName) {
        return rights.stream().anyMatch(right -> right.getName().equals(rightsName));
    }


    public static boolean hisRole(List<Roles> roles, String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }

    public static String enterStringDate(Scanner scanner) {
        while (true) {
            System.out.print("Дата (дд.мм.гг): ");
            String stringDate = scanner.nextLine();
            if (isValidDateFormat(stringDate)) {
                return stringDate;
            }
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
        }
    }

    private static boolean isValidDateFormat(String dateString) {
        // Паттерн для даты в формате "дд.мм.гг"
        String datePattern = "\\d{2}\\.\\d{2}\\.\\d{2}";

        // Проверяем, соответствует ли строка паттерну
        return Pattern.matches(datePattern, dateString);
    }
}
