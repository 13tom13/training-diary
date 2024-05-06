package service;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import entities.model.Rights;
import entities.model.Training;
import entities.model.User;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.service.training.implementation.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;
import utils.mappers.TrainingMapper;
import utils.mappers.UserMapper;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.Utils.getStringFromDate;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest extends TestUtil {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private UserDTO testUser;
    private TrainingDTO testTraining;
    private User UserForRepository;
    private Training trainingForRepository;
    private final String testTrainingName = "Test Training";
    private final String testAdditionalName = "additionalName";

    @BeforeEach
    public void setUp() {
        testUser = createTestUserDTO();
        testUser.getRights().add(new Rights(2L, "WRITE"));
        testUser.getRights().add(new Rights(3L, "DELETE"));
        testUser.getRights().add(new Rights(4L, "EDIT"));
        testTraining = createTestTrainingDTO();
        trainingForRepository = TrainingMapper.INSTANCE.trainingDTOToTraining(testTraining);
        UserForRepository = UserMapper.INSTANCE.userDTOToUser(testUser);
    }

    @Test
    public void testSaveTraining_Successful() throws NoWriteRightsException, RepositoryException {
        // Arrange


        // Act
        trainingService.saveTraining(testUser, testTraining);

        // Assert
        verify(trainingRepository).saveTraining(UserForRepository, trainingForRepository);
    }

    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<LocalDate, TreeSet<TrainingDTO>> expectedTrainings = new TreeMap<>();


        // Act
        TreeMap<LocalDate, TreeSet<TrainingDTO>> actualTrainings = trainingService.getAllTrainings(testUser);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }


    @Test
    public void testDeleteTraining_Successful() throws NoDeleteRightsException, RepositoryException {

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(UserForRepository, TEST_DATE, testTrainingName))
                .thenReturn(trainingForRepository);

        // Act
        trainingService.deleteTraining(testUser, getStringFromDate(TEST_DATE), testTrainingName);

        // Assert
        verify(trainingRepository).deleteTraining(UserForRepository, trainingForRepository);
    }

    @Test
    public void testAddTrainingAdditional_Successful() throws RepositoryException, NoWriteRightsException {
        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(UserForRepository, TEST_DATE, testTrainingName))
                .thenReturn(trainingForRepository);

        // Act
        trainingService.addTrainingAdditional(testUser, testTraining, testAdditionalName, "additionalValue");

        // Assert
        verify(trainingRepository).updateTraining(UserForRepository, trainingForRepository, trainingForRepository);
    }


    @Test
    public void testRemoveTrainingAdditional_Successful() throws RepositoryException, NoEditRightsException {
        // Arrange
        trainingForRepository.addAdditional(testAdditionalName, "additionalValue");

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(UserForRepository, TEST_DATE, testTrainingName))
                .thenReturn(trainingForRepository);

        // Act
        trainingService.removeTrainingAdditional(testUser, testTraining, testAdditionalName);

        // Assert
        verify(trainingRepository).updateTraining(UserForRepository, trainingForRepository, trainingForRepository);
    }

}
