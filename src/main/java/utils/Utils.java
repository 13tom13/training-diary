package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Utils {
    public static final String DATE_FORMAT = "dd.MM.yy";


    public static ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    public static LocalDate getDateFromString(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(dateString, formatter);
    }



    public static String getFormattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
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

            System.out.println("\n" + "=====" + currentDate + "=====");

            for (TrainingDTO training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}
