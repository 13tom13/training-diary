package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для ведения журнала событий.
 */
public class Logger {
    private static Logger instance;
    public final static String LOGS_PATH = "logs";

    private Logger() {
    }

    /**
     * Получает экземпляр Logger (одиночка).
     *
     * @return Экземпляр Logger
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Записывает действие пользователя в журнал.
     *
     * @param userEmail Email пользователя
     * @param action    Описание действия
     */
    public synchronized void logAction(String userEmail, String action) {
        String formattedTime = getCurrentTimeFormatted();
        String logMessage = "%s: ACTION - %s \"%s\"".formatted(formattedTime, userEmail, action);
        logToFile(userEmail, logMessage);
    }

    /**
     * Записывает ошибку в журнал.
     *
     * @param userEmail    Email пользователя
     * @param errorMessage Описание ошибки
     */
    public synchronized void logError(String userEmail, String errorMessage) {
        String formattedTime = getCurrentTimeFormatted();
        String logMessage = "%s: ERROR - %s \"%s\"".formatted(formattedTime, userEmail, errorMessage);
        logToFile(userEmail, logMessage);
    }

    /**
     * Записывает сообщение в файл журнала.
     *
     * @param userEmail   Email пользователя, для которого записывается сообщение в журнал
     * @param logMessage  Сообщение для записи в журнал
     */
    private synchronized void logToFile(String userEmail, String logMessage) {
        String fileName = getLogFielPath(userEmail);
        File directory = new File(LOGS_PATH);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Failed to create log directory: " + LOGS_PATH);
                return;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            System.err.println("Failed to log message: " + e.getMessage());
        }
    }


    /**
     * Получает текущее время в отформатированном виде.
     *
     * @return Текущее время в формате "yyyy-MM-dd HH:mm:ss"
     */
    private String getCurrentTimeFormatted() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Возвращает путь к файлу журнала для конкретного пользователя.
     *
     * @param userEmail Email пользователя
     * @return Путь к файлу журнала для данного пользователя
     */
    public static String getLogFielPath (String userEmail){
        return LOGS_PATH + "/" + userEmail.replace("@", "_") + ".log";
    }

}
