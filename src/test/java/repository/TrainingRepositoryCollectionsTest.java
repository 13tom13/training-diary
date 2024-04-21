package repository;

import exceptions.RepositoryException;
import in.repository.training.implementation.TrainingRepositoryCollections;
import model.Training;
import in.repository.training.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.TestUtil;

import java.util.TreeMap;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Тестовый класс для проверки {@link TrainingRepositoryCollections}.
 */
public class TrainingRepositoryCollectionsTest {

    private TrainingRepository trainingRepository;

    @BeforeEach
    void setUp() {
        // Создаем новый экземпляр репозитория перед каждым тестом
        trainingRepository = new TrainingRepositoryCollections();
    }

    /**
     * Тестирование сохранения и получения тренировки пользователя.
     */
    @Test
    void testSaveAndRetrieveTraining() throws RepositoryException {
        // Создаем тренировку
        Training training = new Training("Push-ups", "2024-04-19", 30, 100);

        // Сохраняем тренировку
        trainingRepository.saveTraining(TestUtil.TEST_EMAIL, training);

        // Получаем все тренировки пользователя
        TreeMap<String, TreeSet<Training>> userTrainings = trainingRepository.getAllTrainingsByUserID(TestUtil.TEST_EMAIL);

        // Проверяем, что тренировка успешно сохранена
        assertThat(userTrainings).isNotEmpty();
        assertThat(userTrainings).containsKey("2024-04-19");
        assertThat(userTrainings.get("2024-04-19")).contains(training);
    }

    /**
     * Тестирование удаления тренировки пользователя.
     */
    @Test
    void testDeleteTraining() throws RepositoryException {
        // Создаем тренировку
        Training training = new Training("Push-ups", "2024-04-19", 30, 100);

        // Сохраняем тренировку
        trainingRepository.saveTraining(TestUtil.TEST_EMAIL, training);

        // Удаляем тренировку
        trainingRepository.deleteTraining(TestUtil.TEST_EMAIL, training);

        // Проверяем, что тренировка успешно удалена
        TreeMap<String, TreeSet<Training>> userTrainings = trainingRepository.getAllTrainingsByUserID(TestUtil.TEST_EMAIL);
        assertThat(userTrainings).isEmpty();
    }

    /**
     * Тестирование обновления тренировки пользователя.
     */
    @Test
    void testUpdateTraining() throws RepositoryException {
        // Создаем и сохраняем старую тренировку
        Training oldTraining = new Training("Push-ups", "2024-04-19", 30, 100);
        trainingRepository.saveTraining(TestUtil.TEST_EMAIL, oldTraining);

        // Создаем и сохраняем новую тренировку
        Training newTraining = new Training("Sit-ups", "2024-04-19", 20, 80);
        trainingRepository.updateTraining(TestUtil.TEST_EMAIL, oldTraining, newTraining);

        // Получаем все тренировки пользователя
        TreeMap<String, TreeSet<Training>> userTrainings = trainingRepository.getAllTrainingsByUserID(TestUtil.TEST_EMAIL);

        // Проверяем, что старая тренировка удалена, а новая добавлена
        assertThat(userTrainings).isNotEmpty();
        assertThat(userTrainings).containsKey("2024-04-19");
        assertThat(userTrainings.get("2024-04-19")).contains(newTraining);
        assertThat(userTrainings.get("2024-04-19")).doesNotContain(oldTraining);
    }

    /**
     * Тестирование получения несуществующей тренировки пользователя.
     */
    @Test
    void testGetNonExistentTraining() {
        // Пытаемся получить несуществующую тренировку
        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> trainingRepository.getTrainingByUserIDlAndDataAndName(TestUtil.TEST_EMAIL, "2024-04-19", "Push-ups"));
    }
}
