package controller;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.implementation.TrainingControllerImpl;
import in.service.training.TrainingService;
import entities.model.Training;
import entities.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static testutil.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerImplTest {

    @Mock
    private TrainingService trainingServiceMock;

    @InjectMocks
    private TrainingControllerImpl trainingController;

    private User testUser;

    private final String testTrainingName = "Test Training";

    private final String testAdditionalName = "additionalName";

    private Training testTraining;

    @BeforeEach
    public void setUp() {
        // Создание тестового пользователя
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        testTraining  = new Training(testTrainingName, TEST_DATE, 60, 200);

    }


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

    @Test
    public void testDeleteTraining_Successful() throws InvalidDateFormatException, NoDeleteRightsException, RepositoryException {
        // Act
        trainingController.deleteTraining(testUser, TEST_DATE, testTrainingName);

        // Assert
        verify(trainingServiceMock).deleteTraining(testUser, TEST_DATE, testTrainingName);
    }

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

    @Test
    public void testGetTrainingByUserEmailAndDataAndName_ReturnsTraining() throws RepositoryException {
        // Configure mock behavior
        when(trainingServiceMock.getTrainingByUserIDAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        Training actualTraining = trainingController
                .getTrainingByUserEmailAndDateAndName(testUser, TEST_DATE, testTrainingName);

        // Assert
        assertEquals(testTraining, actualTraining);
    }

    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {
        // Arrange
        String testAdditionalValue = "additionalValue";
        when(trainingServiceMock.addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue))
                .thenReturn(testTraining);
        // Act
        trainingController.addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);

        // Assert
        verify(trainingServiceMock).addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);
    }

    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {
        when(trainingServiceMock.removeTrainingAdditional(testUser, testTraining, testAdditionalName))
                .thenReturn(testTraining);

        // Act
        trainingController.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingServiceMock).removeTrainingAdditional(testUser, testTraining, testAdditionalName);
    }

    @Test
    public void testChangeNameTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newName = "New Training Name";
        when(trainingServiceMock.changeNameTraining(testUser, testTraining, newName))
                .thenReturn(testTraining);

        // Act
        trainingController.changeNameTraining(testUser, testTraining, newName);

        // Assert
        verify(trainingServiceMock).changeNameTraining(testUser, testTraining, newName);
    }

    @Test
    public void testChangeDateTraining_Successful() throws RepositoryException, InvalidDateFormatException, NoEditRightsException {
        // Arrange
        Date newDate = new Date(2024, 4, 19);
        when(trainingServiceMock.changeDateTraining(testUser, testTraining, newDate))
                .thenReturn(testTraining);

        // Act
        trainingController.changeDateTraining(testUser, testTraining, newDate);

        // Assert
        verify(trainingServiceMock).changeDateTraining(testUser, testTraining, newDate);
    }

    @Test
    public void testChangeDurationTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newDuration = "60";
        when(trainingServiceMock.changeDurationTraining(testUser, testTraining, Integer.parseInt(newDuration)))
                .thenReturn(testTraining);

        // Act
        trainingController.changeDurationTraining(testUser, testTraining, newDuration);

        // Assert
        verify(trainingServiceMock).changeDurationTraining(testUser, testTraining, 60);
    }

    @Test
    public void testChangeCaloriesTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newCalories = "500";
        when(trainingServiceMock.changeCaloriesTraining(testUser, testTraining, Integer.parseInt(newCalories)))
                .thenReturn(testTraining);

        // Act
        trainingController.changeCaloriesTraining(testUser, testTraining, newCalories);

        // Assert
        verify(trainingServiceMock).changeCaloriesTraining(testUser, testTraining, 500);
    }


}