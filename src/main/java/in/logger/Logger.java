package in.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    public final static String LOGS_PATH = "logs";

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void logAction(String userEmail, String action) {
        String fileName = getLogFielPath(userEmail);
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        File directory = new File(LOGS_PATH);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Failed to create log directory: " + LOGS_PATH);
                return;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(formattedTime + ": " + " user with email " + userEmail + " " + action);
        } catch (IOException e) {
            System.err.println("Failed to log action: " + e.getMessage());
        }
    }

    public static String getLogFielPath (String userEmail){
        return LOGS_PATH + "/" + userEmail.replace("@", "_") + ".log";
    }

}
