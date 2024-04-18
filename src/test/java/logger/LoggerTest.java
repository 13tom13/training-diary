package logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование класса Logger.
 */
public class LoggerTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_ACTION = "logged in";

    /**
     * Удаление существующего файла журнала перед каждым запуском теста.
     */
    @BeforeEach
    public void setUp() {
        String logFilePath = Logger.getLogFielPath(TEST_EMAIL);
        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            assertTrue(logFile.delete(), "Failed to delete existing log file");
        }
    }

    /**
     * Тестирование метода logAction.
     */
    @Test
    public void testLogAction() {
        // Arrange
        Logger logger = Logger.getInstance();

        // Act
        logger.logAction(TEST_EMAIL, TEST_ACTION);

        // Assert
        String logFilePath = Logger.getLogFielPath(TEST_EMAIL);
        File logFile = new File(logFilePath);
        assertTrue(logFile.exists(), "Log file was not created");

        // Check log file content
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line = reader.readLine();
            assertNotNull(line, "Log file is empty");
            String expectedLine = formatLogLine();
            assertEquals(expectedLine, line, "Logged action does not match expected");
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        }
    }

    /**
     * Форматирование строки журнала действий.
     *
     * @return отформатированная строка журнала действий
     */
    private String formatLogLine() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formattedTime + ": ACTION - " + TEST_EMAIL + " \"" + LoggerTest.TEST_ACTION + "\"";
    }
}