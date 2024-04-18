package logger;

import utils.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.TestUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest extends TestUtil {

    private static final String LOGS_PATH = Logger.LOGS_PATH;
    private static final String TEST_ACTION = "logged in";
    private static final String LOG_FILE_NAME = LOGS_PATH + "/" + TEST_EMAIL.replace("@", "_") + ".log";

    @BeforeEach
    public void setUp() {
        File logsDir = new File(LOGS_PATH);
        if (!logsDir.exists()) {
            assertTrue(logsDir.mkdirs(), "Failed to create logs directory");
        }
    }

    @Test
    public void testLogAction() {
        // Arrange
        Logger logger = Logger.getInstance();

        // Act
        logger.logAction(TEST_EMAIL, TEST_ACTION);

        // Assert
        File logFile = new File(LOG_FILE_NAME);
        assertTrue(logFile.exists(), "Log file was not created");

        // Check log file content
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_NAME))) {
            String line = reader.readLine();
            assertNotNull(line, "Log file is empty");
            String expectedLine = formatLogLine();
            assertEquals(expectedLine, line, "Logged action does not match expected");
        } catch (IOException e) {
            fail("Error reading log file: " + e.getMessage());
        }
    }

    private String formatLogLine() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formattedTime + ": " + " user with email " + TEST_EMAIL + " " + TEST_ACTION;
    }
}

