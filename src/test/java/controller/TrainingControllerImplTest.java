package controller;

import in.controller.training.implementation.TrainingControllerImpl;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import model.Training;
import model.User;
import in.service.training.TrainingService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static testutil.TestUtil.*;

/**
 * Тестирование класса TrainingControllerImpl.
 */
@ExtendWith(MockitoExtension.class)
public class TrainingControllerImplTest {

    @Mock
    private TrainingService trainingServiceMock;

    @InjectMocks
    private TrainingControllerImpl trainingController;

    private User testUser;

    private final Training testTraining = new Training();
    

    private final String testTrainingName = "Test Training";

    private final String testAdditionalName = "additionalName";

    @BeforeEach
    public void setUp() {
        // Создание тестового пользователя
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
        doNothing().when(trainingServiceMock).saveTraining(testUser, training);

        // Act
        trainingController.saveTraining(testUser, training);

        // Assert
        verify(trainingServiceMock).saveTraining(testUser, training);
    }

    /**
     * Тестирование получения всех тренировок.
     */
    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<Date, TreeSet<Training>> expectedTrainings = new TreeMap<>();

        // Configure mock behavior
        when(trainingServiceMock.getAllTrainings(testUser)).thenReturn(expectedTrainings);

        // Act
        TreeMap<Date, TreeSet<Training>> actualTrainings = trainingController.getAllTrainings(testUser);

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
        // Configure mock behavior
        doNothing().when(trainingServiceMock).deleteTraining(testUser, TEST_DATE, testTrainingName);

        // Act
        trainingController.deleteTraining(testUser, TEST_DATE, testTrainingName);

        // Assert
        verify(trainingServiceMock).deleteTraining(testUser, TEST_DATE, testTrainingName);
    }

    /**
     * Тестирование получения тренировок по электронной почте пользователя и дате.
     *
     * @throws RepositoryException если возникла ошибка в репозитории
     */
    @Test
    public void testGetTrainingsByUserEmailAndData_ReturnsTrainings() throws RepositoryException {
        // Arrange
        TreeSet<Training> expectedTrainings = new TreeSet<>();

        // Configure mock behavior
        when(trainingServiceMock.getTrainingsByUserIDAndData(testUser, TEST_DATE)).thenReturn(expectedTrainings);

        // Act
        TreeSet<Training> actualTrainings = trainingController.getTrainingsByUserEmailAndData(testUser, TEST_DATE);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }

    /**
     * Тестирование получения тренировки по электронной почте пользователя, дате и имени тренировки.
     *
     * @throws RepositoryException если возникла ошибка в репозитории
     */
    @Test
    public void testGetTrainingByUserEmailAndDataAndName_ReturnsTraining() throws RepositoryException {
        // Configure mock behavior
        when(trainingServiceMock.getTrainingByUserIDAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        Training actualTraining = trainingController
                .getTrainingByUserEmailAndDataAndName(testUser, TEST_DATE, testTrainingName);

        // Assert
        assertEquals(testTraining, actualTraining);
    }

    /**
     * Тестирование успешного добавления дополнительной информации о тренировке.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoWriteRightsException если нет прав на запись
     */
    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {
        // Act
        String testAdditionalValue = "additionalValue";
        trainingController.addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);

        // Assert
        verify(trainingServiceMock).addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);
    }

    /**
     * Тестирование успешного удаления дополнительной информации о тренировке.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoEditRightsException если нет прав на редактирование
     */
    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {
        // Act
        trainingController.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingServiceMock).removeTrainingAdditional(testUser, testTraining, testAdditionalName);
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
        String newName = "New Training Name";

        // Act
        trainingController.changeNameTraining(testUser, testTraining, newName);

        // Assert
        verify(trainingServiceMock).changeNameTraining(testUser, testTraining, newName);
    }

    /**
     * Тестирование успешного изменения даты тренировки.
     *
     * @throws RepositoryException          если возникла ошибка в репозитории
     * @throws InvalidDateFormatException если формат даты неверен
     * @throws NoEditRightsException       если нет прав на редактирование
     */
    @Test
    public void testChangeDateTraining_Successful() throws RepositoryException, InvalidDateFormatException, NoEditRightsException {
        // Arrange
        Date newDate = new Date(2024,4,19);

        // Act
        trainingController.changeDateTraining(testUser, testTraining, newDate);

        // Assert
        verify(trainingServiceMock).changeDateTraining(testUser, testTraining, newDate);
    }

    /**
     * Тестирование успешного изменения продолжительности тренировки.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoEditRightsException если нет прав на редактирование
     */
    @Test
    public void testChangeDurationTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newDuration = "60";

        // Act
        trainingController.changeDurationTraining(testUser, testTraining, newDuration);

        // Assert
        verify(trainingServiceMock).changeDurationTraining(testUser, testTraining, 60);
    }

    /**
     * Тестирование успешного изменения количества калорий, сожженных во время тренировки.
     *
     * @throws RepositoryException  если возникла ошибка в репозитории
     * @throws NoEditRightsException если нет прав на редактирование
     */
    @Test
    public void testChangeCaloriesTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newCalories = "500";

        // Act
        trainingController.changeCaloriesTraining(testUser, testTraining, newCalories);

        // Assert
        verify(trainingServiceMock).changeCaloriesTraining(testUser, testTraining, 500);
    }

}