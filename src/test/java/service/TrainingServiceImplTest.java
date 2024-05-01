package service;

import entities.model.User;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.service.training.implementation.TrainingServiceImpl;
import entities.model.Rights;
import entities.model.Training;
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
    private final Training testTraining = new Training(testTrainingName, TEST_DATE, 60, 200);

    @BeforeEach
    public void setUp() {
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        testUser.getRights().add(new Rights(1L,"WRITE"));
        testUser.getRights().add(new Rights(2L,"EDIT"));
        testUser.getRights().add(new Rights(3L,"DELETE"));
    }

    @Test
    public void testSaveTraining_Successful() throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        // Arrange
        Training training = new Training(testTrainingName, TEST_DATE, 60, 200);

        // Act
        trainingService.saveTraining(testUser, training);

        // Assert
        verify(trainingRepository).saveTraining(testUser, training);
    }

    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<Date, TreeSet<Training>> expectedTrainings = new TreeMap<>();


        // Act
        TreeMap<Date, TreeSet<Training>> actualTrainings = trainingService.getAllTrainings(testUser);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }


    @Test
    public void testDeleteTraining_Successful() throws InvalidDateFormatException, NoDeleteRightsException, RepositoryException {

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        trainingService.deleteTraining(testUser, TEST_DATE, testTrainingName);

        // Assert
        verify(trainingRepository).deleteTraining(testUser, testTraining);
    }

    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {
        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        trainingService.addTrainingAdditional(testUser, testTraining, testAdditionalName, "additionalValue");

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
    }


    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        testTraining.addAdditional(testAdditionalName, "additionalValue");

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        trainingService.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
    }


    @Test
    public void testChangeNameTraining_Successful() throws RepositoryException, NoEditRightsException {

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(testUser, TEST_DATE, testTrainingName))
                .thenReturn(testTraining);

        // Act
        trainingService.changeNameTraining(testUser, testTraining, "New Training Name");

        // Assert
        verify(trainingRepository).updateTraining(testUser, testTraining, testTraining);
    }
}
