package in.logger.log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void logAction(String userEmail, String action) {
        String directoryPath = "in/logger/log";
        String fileName = directoryPath + "/" + userEmail.replace("@", "_") + ".log";
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(formattedTime + ": " + action);
        } catch (IOException e) {
            System.err.println("Failed to log action: " + e.getMessage());
        }
    }
}

