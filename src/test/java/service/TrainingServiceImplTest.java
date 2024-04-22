package service;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.service.training.implementation.TrainingServiceImpl;
import model.Training;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.Date;
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
        Training training = new Training(testTrainingName, TEST_DATE, 60, 200);

        // Configure mock behavior
        doNothing().when(trainingRepository).saveTraining(testUser, training);

        // Act
        trainingService.saveTraining(testUser, training);

        // Assert
        verify(trainingRepository).saveTraining(testUser, training);
    }

    /**
     * Тестирование получения всех тренировок.
     */
    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<String, TreeSet<Training>> expectedTrainings = new TreeMap<>();

        // Configure mock behavior
//        when(trainingRepository.getAllTrainingsByUserID(testUser).thenReturn(expectedTrainings));

        // Act
        TreeMap<Date, TreeSet<Training>> actualTrainings = trainingService.getAllTrainings(testUser);

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
        Training testTraining = new Training(testTrainingName, TEST_DATE, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserIDlAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).deleteTraining(testUser, testTraining);

        // Act
        trainingService.deleteTraining(testUser, TEST_DATE, testTrainingName);

        // Assert
        verify(trainingRepository).deleteTraining(testUser, testTraining);
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
        Training testTraining = new Training(testTrainingName, TEST_DATE, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserIDlAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser, testTraining, testTraining);

        // Act
        trainingService.addTrainingAdditional(testUser, testTraining, testAdditionalName, "additionalValue");

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
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
        Training testTraining = new Training(testTrainingName, TEST_DATE, 60, 200);
        testTraining.addAdditional(testAdditionalName, "additionalValue");

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserIDlAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser, testTraining, testTraining);

        // Act
        trainingService.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
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
        Training testTraining = new Training(testTrainingName, TEST_DATE, 60, 200);

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserIDlAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);
        doNothing().when(trainingRepository).updateTraining(testUser, testTraining, testTraining);

        // Act
        trainingService.changeNameTraining(testUser, testTraining, "New Training Name");

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
    }
}
