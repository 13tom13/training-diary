package utils;

import entities.dto.UserDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static final String DATE_FORMAT = "dd.MM.yy";

    public static Date getDateFromString(String dateString) throws ParseException {
            // Парсинг строки в формате "дд.мм.гг" в объект Date
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(dateString);

    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }
    public static boolean hisRight(UserDTO userDTO, String rightsName) {
        return userDTO.getRights().stream().anyMatch(rights -> rights.getName().equals(rightsName));
    }


}
