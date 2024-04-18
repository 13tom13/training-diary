package testutil;

import org.junit.jupiter.api.AfterAll;

import java.io.File;

import static utils.Logger.getLogFielPath;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Утилитарный класс для тестирования.
 */
public abstract class TestUtil {

    /** Тестовый email. */
    public static final String TEST_EMAIL = "test@test.com";

    /**
     * Метод, выполняющийся после всех тестов для очистки журналов.
     */
    @AfterAll
    public static void cleanLogsAfterTests() {
        File logFile = new File(getLogFielPath(TEST_EMAIL));
        if (logFile.exists()) {
            assertTrue(logFile.delete(), "Failed to delete log file");
        }
    }
}
