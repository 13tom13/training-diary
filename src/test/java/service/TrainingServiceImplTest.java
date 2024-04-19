package service;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.TrainingRepository;
import in.repository.TrainingTypeRepository;
import in.service.training.implementation.TrainingServiceImpl;
import model.Rights;
import model.Training;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса TrainingServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest extends TestUtil {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private User testUser;
    private final String testDate = "10.11.24";
    private final String testTrainingName = "Test Training";
    private final String testAdditionalName = "additionalName";

    @BeforeEach
    public void setUp() {
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
    }

    /**
     * Тестирование успешного сохранения тренировки.
     *
     * @throws InvalidDateFormatException если формат даты неверен
     * @throws NoWriteRightsException     если нет прав на запись
     * @throws RepositoryException        если возникла ошибка в репозитории
     */
    @Test
    public void testSaveTraining_Successful() throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        // Arrange
        Training training = new Training(testTrainingName, testDate, 60, 200);

        // Configure mock behavior
        doNothing().when(trainingRepository).saveTraining(testUser.getEmail(), training);

        // Act
        trainingService.saveTraining(testUser, training);

        // Assert
        verify(trainingRepository).saveTraining(testUser.getEmail(), training);
    }

    /**
     * Тестирование получения всех тренировок.
     */
    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<String, TreeSet<Training>> expectedTrainings = new TreeMap<>();

        // Configure mock behavior
        when(trainingRepository.getAllTrainingsByUserEmail(testUser.getEmail())).thenReturn(expectedTrainings);

        // Act
        TreeMap<String, TreeSet<Training>> actualTrainings = trainingService.getAllTrainings(testUser);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }

    /**
     * Тестирование успешного удаления тренировки.
     *
     * @throws InvalidDateFormatException если формат даты неверен
     * @throws NoDeleteRightsException    если нет прав на удаление
     * @throws RepositoryException        если возникла ошибка в репозитории
     */
    @Test
    public void testDeleteTraining_Successful() throws InvalidDateFormatException, NoDeleteRightsException, RepositoryException {
        // Arrange
        Training testTraining = new Training(testTrainingName, testDate, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser.getEmail(), testDate, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).deleteTraining(testUser.getEmail(), testTraining);

        // Act
        trainingService.deleteTraining(testUser, testDate, testTrainingName);

        // Assert
        verify(trainingRepository).deleteTraining(testUser.getEmail(), testTraining);
    }

    /**
     * Тестирование успешного добавления дополнительной информации о тренировке.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoWriteRightsException если нет прав на запись
     */
    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {
        // Arrange
        Training testTraining = new Training(testTrainingName, testDate, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser.getEmail(), testDate, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);

        // Act
        trainingService.addTrainingAdditional(testUser, testTraining, testAdditionalName, "additionalValue");

        // Assert
        verify(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);
    }

    /**
     * Тестирование успешного удаления дополнительной информации о тренировке.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoEditRightsException если нет прав на редактирование
     */
    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        Training testTraining = new Training(testTrainingName, testDate, 60, 200);
        testTraining.addAdditional(testAdditionalName, "additionalValue");

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser.getEmail(), testDate, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);

        // Act
        trainingService.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);
    }

    /**
     * Тестирование успешного изменения имени тренировки.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoEditRightsException если нет прав на редактирование
     */
    @Test
    public void testChangeNameTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        Training testTraining = new Training(testTrainingName, testDate, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser.getEmail(), testDate, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);

        // Act
        trainingService.changeNameTraining(testUser, testTraining, "New Training Name");

        // Assert
        verify(trainingRepository).updateTraining(testUser.getEmail(), testTraining, testTraining);
    }
}
