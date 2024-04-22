package utils;

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

}
