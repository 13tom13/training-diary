package repository;

import entities.model.User;
import exceptions.RepositoryException;
import in.repository.training.implementation.TrainingRepositoryJDBC;
import entities.model.Training;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static testutil.TestUtil.*;

public class TrainingRepositoryJDBCTest {

    private static Connection connection;
    private static TrainingRepositoryJDBC repository;

    private static User user;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = createConnectionToTestDatabase();
        repository = new TrainingRepositoryJDBC(connection);

        user = getTestUserFromDatabase(connection);
    }


    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    void testSaveTraining() throws RepositoryException {
        // Создаем новую тренировку для пользователя
        Training newTraining = new Training("Йога", TEST_DATE, 60, 300);

        // Сохраняем тренировку
        repository.saveTraining(user, newTraining);

        // Получаем все тренировки пользователя
        TreeMap<Date, TreeSet<Training>> userTrainings = repository.getAllTrainingsByUserID(user);

        // Проверяем, что тренировка успешно сохранена
        Assertions.assertTrue(userTrainings.containsValue(new TreeSet<>(Set.of(newTraining))));
    }


    @Test
    void testGetAllTrainingsByUserID() throws RepositoryException {
        // Предположим, что у пользователя есть две тренировки
        Date date = TEST_DATE;
        Date nextDay = new Date(date.getTime() + 24 * 60 * 60 * 1000); // Добавляем один день
        Training training1 = new Training("Силовая", date, 60, 300);
        Training training2 = new Training("Велосипед", nextDay, 45, 200);

        // Сохраняем тренировки в базу данных
        repository.saveTraining(user, training1);
        repository.saveTraining(user, training2);

        // Получаем все тренировки пользователя
        TreeMap<Date, TreeSet<Training>> userTrainings = repository.getAllTrainingsByUserID(user);

        // Проверяем, что количество тренировок равно 2
        assertEquals(2, userTrainings.size());

        // Проверяем, что тренировки отсортированы по дате
        Set<Date> dates = userTrainings.keySet();
        Date prevDate = null;
        for (Date currentDate : dates) {
            if (prevDate != null) {
                Assertions.assertTrue(prevDate.before(currentDate));
            }
            prevDate = currentDate;
        }
    }

    @Test
    void testGetTrainingByUserIDlAndDataAndName() throws RepositoryException {
        // Предположим, что у пользователя есть тренировка на указанную дату с указанным именем
        Date date = TEST_DATE;
        String trainingName = "Ходьба";
        Training expectedTraining = new Training(trainingName, date, 60, 300);

        // Сохраняем тренировку в базу данных
        repository.saveTraining(user, expectedTraining);

        // Получаем тренировку пользователя по указанным параметрам
        Training actualTraining = repository.getTrainingByUserIDlAndDataAndName(user, date, trainingName);

        // Проверяем, что полученная тренировка соответствует ожидаемой
        assertEquals(expectedTraining, actualTraining);

        // Проверяем, что метод выбрасывает исключение, если тренировка не найдена
        String nonExistentTrainingName = "Плавание"; // Несуществующее имя тренировки
        assertThrows(RepositoryException.class, () -> repository.getTrainingByUserIDlAndDataAndName(user, date, nonExistentTrainingName));
    }


    @Test
    void testDeleteTraining() throws RepositoryException {
        // Предположим, что у пользователя есть тренировка, которую мы хотим удалить
        Training training = new Training("Бег", TEST_DATE, 60, 300);

        // Сохраняем тренировку в базу данных
        repository.saveTraining(user, training);

        // Удаляем тренировку
        boolean isDeleted = repository.deleteTraining(user, training);

        // Проверяем, что метод возвращает true, если удаление прошло успешно
        Assertions.assertTrue(isDeleted);

        // Проверяем, что тренировка больше не существует в базе данных
        assertThrows(RepositoryException.class, () -> repository.getTrainingByUserIDlAndDataAndName(user, TEST_DATE, "Running"));

        // Проверяем, что метод выбрасывает исключение, если тренировка для удаления не найдена
        assertThrows(RepositoryException.class, () -> repository.deleteTraining(user, training));
    }


    @Test
    void testUpdateTraining() throws RepositoryException {
        // Создаем старую и новую тренировки
        String oldTrainingName = "Лежание";
        String newTrainingName = "Стояние";
        Training oldTraining = new Training(oldTrainingName, TEST_DATE, 60, 300);
        Training newTraining = new Training(newTrainingName, new Date(TEST_DATE.getTime() + 48 * 60 * 60 * 1000), 45, 200);

        // Сохраняем старую тренировку
        repository.saveTraining(user, oldTraining);

        // Обновляем тренировку
        repository.updateTraining(user, oldTraining, newTraining);

        // Получаем обновленную тренировку из базы данных
        Training updatedTraining = repository.getTrainingByUserIDlAndDataAndName(user, newTraining.getDate(), newTrainingName);

        // Проверяем, что тренировка успешно обновлена
        assertNotNull(updatedTraining);
        assertEquals(newTraining.getName(), updatedTraining.getName());
        assertEquals(newTraining.getDate(), updatedTraining.getDate());
        assertEquals(newTraining.getDuration(), updatedTraining.getDuration());
        assertEquals(newTraining.getCaloriesBurned(), updatedTraining.getCaloriesBurned());
    }

    @Test
    void testSaveTrainingAdditionals() throws RepositoryException {
        // Создаем тренировку с дополнительной информацией
        String trainingName = "Поход";
        HashMap<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("Погода", "Солнце");
        additionalInfo.put("Температура", "25°C");

        Training trainingWithAdditionalInfo = new Training(trainingName, TEST_DATE, 60, 300, additionalInfo);

        // Сохраняем дополнительную информацию
        repository.saveTraining(user, trainingWithAdditionalInfo);

        // Получаем сохраненную дополнительную информацию из базы данных
        Training savedTraining = repository.getTrainingByUserIDlAndDataAndName(user, TEST_DATE, trainingName);
        HashMap<String, String> savedAdditionalInfo = savedTraining.getAdditions();

        // Проверяем, что дополнительная информация успешно сохранена
        assertNotNull(savedAdditionalInfo);
        assertEquals(additionalInfo, savedAdditionalInfo);
    }


}
