package service;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingService;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingStatisticsServiceImpTest extends TestUtil {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingStatisticsServiceImp trainingStatisticsService;

    private UserDTO testUser;
    private final LocalDate startDate = LocalDate.of(2024, 1, 1);
    private final LocalDate endDate = LocalDate.of(2024, 12, 31);

    private int totalTestTrainings;

    private int totalTestDuration;

    private int totalTestCaloriesBurned;

    @BeforeEach
    public void setUp() {
        testUser = createTestUserDTO();
    }

    @Test
    public void testGetAllTrainingStatistics() throws NoStatisticsRightsException {
        // Arrange
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTrainings = new TreeMap<>();
        allTrainings.put(LocalDate.of(2024, 1, 1), new TreeSet<>());
        allTrainings.put(LocalDate.of(2024, 1, 2), new TreeSet<>());

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser.getEmail())).thenReturn(allTrainings);

        // Act
        int totalTrainings = trainingStatisticsService.getAllTrainingStatistics(testUser);

        // Assert
        assertEquals(0, totalTrainings);
    }


    @Test
    public void testGetAllTrainingStatisticsPerPeriod() throws NoStatisticsRightsException {
        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser.getEmail())).thenReturn(getMockTrainingData());

        // Act
        Integer totalTrainings = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestTrainings, totalTrainings);
    }

    @Test
    public void testGetDurationStatisticsPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser.getEmail())).thenReturn(getMockTrainingData());

        // Act
        Integer totalDuration = trainingStatisticsService.getDurationStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestDuration, totalDuration);
    }


    @Test
    public void testGetCaloriesBurnedPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser.getEmail())).thenReturn(getMockTrainingData());

        // Act
        Integer totalCaloriesBurned = trainingStatisticsService.getCaloriesBurnedPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestCaloriesBurned, totalCaloriesBurned);
    }


    private TreeMap<LocalDate, TreeSet<TrainingDTO>> getMockTrainingData() {
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTrainings = new TreeMap<>();
        TreeSet<TrainingDTO> trainings = new TreeSet<>();
//        trainings.add(new TrainingDTO("Test Training 1", TEST_DATE, 60, 200));
//        trainings.add(new TrainingDTO("Test Training 2", TEST_DATE, 45, 150));
//        trainings.add(new TrainingDTO("Test Training 3", TEST_DATE, 90, 300));
        allTrainings.put(TEST_DATE, trainings);
        totalTestTrainings = trainings.size();
        for (TrainingDTO training : trainings) {
            totalTestDuration += training.getDuration();
            totalTestCaloriesBurned += training.getCaloriesBurned();
        }
        return allTrainings;
    }
}
