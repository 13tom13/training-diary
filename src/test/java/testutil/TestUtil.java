package testutil;

import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.util.Date;

import static utils.Logger.getLogFielPath;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Утилитарный класс для тестирования.
 */
public abstract class TestUtil {

    /** Тестовое имя пользователя. */
    public static final String TEST_FIRST_NAME = "John";

    /** Тестовая фамилия пользователя. */
    public static final String TEST_LAST_NAME = "Doe";

    /** Тестовый email. */
    public static final String TEST_EMAIL = "test@test.com";

    /** Тестовый пароль. */
    public static final String TEST_PASSWORD = "password";

    /** Тестовая дата. */
    public static final Date TEST_DATE = new Date(2024,4,10);

    /**
     * Метод, выполняющийся после всех тестов для очистки журналов.
     */
    @AfterAll
    public static void cleanLogsAfterTests() {
        // Получаем путь к файлу журнала для тестового email
        String logFilePath = getLogFielPath(TEST_EMAIL);

        // Создаем объект файла для удаления
        File logFile = new File(logFilePath);

        // Проверяем существует ли файл журнала
        if (logFile.exists()) {
            // Удаляем файл журнала
            assertTrue(logFile.delete(), "Failed to delete log file");
        }
    }


}
