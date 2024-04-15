package controller;

import in.controller.implementation.TrainingControllerImpl;
import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.exception.security.rights.NoDeleteRightsException;
import in.exception.security.rights.NoEditRightsException;
import in.exception.security.rights.NoWriteRightsException;
import in.model.Training;
import in.model.User;
import in.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testutil.TestUtil;

import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingControllerImplTest extends TestUtil {

    @Mock
    private TrainingService trainingServiceMock;

    private TrainingControllerImpl trainingController;

    private User testUser;

    private final Training testTraining = new Training();

    private final String testDate = "10.11.24";

    private final String testTrainingName = "Test Training";

    private final String testAdditionalName = "additionalName";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingController = new TrainingControllerImpl(trainingServiceMock);

        // Create test user
        String firstname = "Test";
        String lastname = "User";
        String password = "pass";
        testUser = new User(firstname, lastname, TEST_EMAIL, password);
    }

    @Test
    public void testSaveTraining_Successful() throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        // Arrange;
        Training training = new Training(testTrainingName, testDate, 60, 200);

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
        TreeMap<String, TreeSet<Training>> expectedTrainings = new TreeMap<>();

        // Configure mock behavior
        when(trainingServiceMock.getAllTrainings(testUser)).thenReturn(expectedTrainings);

        // Act
        TreeMap<String, TreeSet<Training>> actualTrainings = trainingController.getAllTrainings(testUser);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }

    @Test
    public void testDeleteTraining_Successful() throws InvalidDateFormatException, NoDeleteRightsException, RepositoryException {

        // Configure mock behavior
        doNothing().when(trainingServiceMock).deleteTraining(testUser, testDate, testTrainingName);

        // Act
        trainingController.deleteTraining(testUser, testDate, testTrainingName);

        // Assert
        verify(trainingServiceMock).deleteTraining(testUser, testDate, testTrainingName);
    }

    @Test
    public void testGetTrainingsByUserEmailAndData_ReturnsTrainings() throws RepositoryException {
        // Arrange
        TreeSet<Training> expectedTrainings = new TreeSet<>();

        // Configure mock behavior
        when(trainingServiceMock.getTrainingsByUserEmailAndData(testUser, testDate)).thenReturn(expectedTrainings);

        // Act
        TreeSet<Training> actualTrainings = trainingController.getTrainingsByUserEmailAndData(testUser, testDate);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }

    @Test
    public void testGetTrainingByUserEmailAndDataAndName_ReturnsTraining() throws RepositoryException {

        // Configure mock behavior
        when(trainingServiceMock.getTrainingByUserEmailAndDataAndName(testUser, testDate, testTrainingName))
                .thenReturn(testTraining);

        // Act
        Training actualTraining = trainingController
                .getTrainingByUserEmailAndDataAndName(testUser, testDate, testTrainingName);

        // Assert
        assertEquals(testTraining, actualTraining);
    }

    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {

        // Act
        String testAdditionalValue = "additionalValue";
        trainingController.addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);

        // Assert
        verify(trainingServiceMock).addTrainingAdditional(testUser, testTraining, testAdditionalName, testAdditionalValue);
    }

    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {

        // Act
        trainingController.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingServiceMock).removeTrainingAdditional(testUser, testTraining, testAdditionalName);
    }

    @Test
    public void testChangeNameTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newName = "New Training Name";

        // Act
        trainingController.changeNameTraining(testUser, testTraining, newName);

        // Assert
        verify(trainingServiceMock).changeNameTraining(testUser, testTraining, newName);
    }

    @Test
    public void testChangeDateTraining_Successful() throws RepositoryException, InvalidDateFormatException, NoEditRightsException {
        // Arrange
        String newDate = "11.11.24";

        // Act
        trainingController.changeDateTraining(testUser, testTraining, newDate);

        // Assert
        verify(trainingServiceMock).changeDateTraining(testUser, testTraining, newDate);
    }

    @Test
    public void testChangeDurationTraining_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        String newDuration = "60";

        // Act
        trainingController.changeDurationTraining(testUser, testTraining, newDuration);

        // Assert
        verify(trainingServiceMock).changeDurationTraining(testUser, testTraining, 60);
    }

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
