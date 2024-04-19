package repository;

import in.repository.TrainingTypeRepository;
import in.repository.implementation.TrainingTypeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static testutil.TestUtil.TEST_EMAIL;

/**
 * Тестовый класс для проверки {@link TrainingTypeRepositoryImpl}.
 */
public class TrainingTypeRepositoryImplTest {

    private TrainingTypeRepository trainingTypeRepository;

    @BeforeEach
    void setUp() {
        // Создаем новый экземпляр репозитория перед каждым тестом
        trainingTypeRepository = new TrainingTypeRepositoryImpl();
    }

    /**
     * Тестирование получения списка тренировок по умолчанию.
     */
    @Test
    void testGetDefaultTrainingTypes() {
        // Получаем список тренировок по умолчанию
        List<String> defaultTrainingTypes = trainingTypeRepository.getTrainingTypes("default");

        // Проверяем, что список не пустой и содержит ожидаемые типы тренировок
        assertThat(defaultTrainingTypes).isNotEmpty();
        assertThat(defaultTrainingTypes).containsExactly("Кардио", "Силовая");
    }

    /**
     * Тестирование получения списка тренировок для нового пользователя.
     */
    @Test
    void testGetTrainingTypesForNewUser() {
        // Получаем список тренировок для нового пользователя
        List<String> userTrainingTypes = trainingTypeRepository.getTrainingTypes(TEST_EMAIL);

        // Проверяем, что список не пустой и совпадает со списком по умолчанию
        assertThat(userTrainingTypes).isNotEmpty();
        assertThat(userTrainingTypes).containsExactly("Кардио", "Силовая");
    }

    /**
     * Тестирование добавления нового типа тренировки для пользователя.
     */
    @Test
    void testSaveTrainingTypeForUser() {
        // Добавляем новый тип тренировки для пользователя
        trainingTypeRepository.saveTrainingType(TEST_EMAIL, "Yoga");

        // Получаем список тренировок для этого пользователя
        List<String> userTrainingTypes = trainingTypeRepository.getTrainingTypes(TEST_EMAIL);

        // Проверяем, что новый тип тренировки успешно добавлен
        assertThat(userTrainingTypes).isNotEmpty();
        assertThat(userTrainingTypes).contains("Yoga");
    }
}
