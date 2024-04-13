package in.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidationService {

    public static final String DATE_FORMAT = "dd.MM.yy";

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
