package utils;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
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

    public static boolean hisRight(UserDTO userDTO, String rightsName) {
        return userDTO.getRights().stream().anyMatch(rights -> rights.getName().equals(rightsName));
    }


    public static boolean hisRole(UserDTO userDTO, String roleName) {
        return userDTO.getRoles().stream().anyMatch(role -> role.getName().equals(roleName));
    }


    public static void printAllTraining(TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining) {
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<LocalDate, TreeSet<TrainingDTO>> entry : allTraining.entrySet()) {
            LocalDate currentDate = entry.getKey();
            TreeSet<TrainingDTO> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + getStringFromDate(currentDate) + "=====");

            for (TrainingDTO training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
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
