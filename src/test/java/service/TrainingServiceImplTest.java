package service;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
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
import servlet.mappers.TrainingMapper;
import servlet.mappers.UserMapper;
import testutil.TestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private UserDTO testUser;
    private TrainingDTO testTraining;
    private User UserForRepository;
    private Training trainingForRepository;
    private final String testTrainingName = "Test Training";
    private final String testAdditionalName = "additionalName";

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO();
        testUser.setFirstName(TEST_FIRST_NAME);
        testUser.setLastName(TEST_LAST_NAME);
        testUser.setEmail(TEST_EMAIL);
        testUser.setRights(new ArrayList<>());
        testUser.setRoles(new ArrayList<>());
        testUser.setActive(true);
        testUser.getRights().add(new Rights(1L,"WRITE"));
        testUser.getRights().add(new Rights(2L,"EDIT"));
        testUser.getRights().add(new Rights(3L,"DELETE"));
        testTraining = new TrainingDTO(testTrainingName, TEST_DATE, 60, 200);
        trainingForRepository = TrainingMapper.INSTANCE.trainingDTOToTraining(testTraining);
        UserForRepository = UserMapper.INSTANCE.userDTOToUser(testUser);
    }

    @Test
    public void testSaveTraining_Successful() throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        // Arrange



        // Act
        trainingService.saveTraining(testUser, testTraining);

        // Assert
        verify(trainingRepository).saveTraining(UserForRepository, trainingForRepository);
    }

    @Test
    public void testGetAllTrainings_ReturnsAllTrainings() {
        // Arrange
        TreeMap<Date, TreeSet<TrainingDTO>> expectedTrainings = new TreeMap<>();


        // Act
        TreeMap<LocalDate, TreeSet<TrainingDTO>> actualTrainings = trainingService.getAllTrainings(testUser);

        // Assert
        assertEquals(expectedTrainings, actualTrainings);
    }


    @Test
    public void testDeleteTraining_Successful() throws InvalidDateFormatException, NoDeleteRightsException, RepositoryException {

        // Configure mock behavior
        when(trainingRepository.getTrainingByUserEmailAndDataAndName(UserForRepository, TEST_DATE, testTrainingName))
                .thenReturn(trainingForRepository);

        // Act
        trainingService.deleteTraining(testUser, String.valueOf(TEST_DATE), testTrainingName);

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
