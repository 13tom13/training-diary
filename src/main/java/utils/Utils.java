package utils;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public static final String DATE_FORMAT = "dd.MM.yy";

    public static Date getDateFromString(String dateString) throws ParseException {
        // Парсинг строки в формате "дд.мм.гг" в объект Date
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.parse(dateString);

    }

    public static Date UnixStringToDate(String unixString) throws ParseException {
        long milliseconds = Long.parseLong(unixString);
        Date date = new Date(milliseconds);
        System.out.println(date);
        return date;
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
//        String dateString = dateFormat.format(date);
//        return dateFormat.parse(dateString);
    }

    public static Date formatDate(String inputDate) {
        long milliseconds = Long.parseLong(inputDate);
        Date dateUnix = new Date(milliseconds);
        DateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yy");

        try {
            Date formUnixDate = inputDateFormat.parse(inputDateFormat.format(dateUnix));
            String formattedDateString = outputDateFormat.format(formUnixDate);
            return outputDateFormat.parse(formattedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static boolean hisRight(UserDTO userDTO, String rightsName) {
        return userDTO.getRights().stream().anyMatch(rights -> rights.getName().equals(rightsName));
    }


    public static boolean hisRole(UserDTO userDTO, String roleName) {
        return userDTO.getRoles().stream().anyMatch(role -> role.getName().equals(roleName));
    }


    public static void printAllTraining(TreeMap<Date, TreeSet<TrainingDTO>> allTraining) {
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<Date, TreeSet<TrainingDTO>> entry : allTraining.entrySet()) {
            String currentDate = getFormattedDate(entry.getKey());
            TreeSet<TrainingDTO> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + currentDate + "=====");

            for (TrainingDTO training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}
