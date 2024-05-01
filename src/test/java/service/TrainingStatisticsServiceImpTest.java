package service;

import entities.model.User;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingService;
import in.service.training.implementation.TrainingStatisticsServiceImp;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingStatisticsServiceImpTest extends TestUtil {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingStatisticsServiceImp trainingStatisticsService;

    private User testUser;
    private final Date startDate = new Date(2024,1,1);
    private final Date endDate = new Date(2024,12,31);

    private int totalTestTrainings;

    private int totalTestDuration;

    private int totalTestCaloriesBurned;

    @BeforeEach
    public void setUp() {
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        testUser.getRights().add(new Rights(4L,"STATISTICS"));
    }

    @Test
    public void testGetAllTrainingStatistics() throws NoStatisticsRightsException {
        // Arrange
        TreeMap<Date, TreeSet<Training>> allTrainings = new TreeMap<>();
        allTrainings.put(new Date(2024,1,1), new TreeSet<>());
        allTrainings.put(new Date(2024,1,2), new TreeSet<>());

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(allTrainings);

        // Act
        int totalTrainings = trainingStatisticsService.getAllTrainingStatistics(testUser);

        // Assert
        assertEquals(0, totalTrainings);
    }


    @Test
    public void testGetAllTrainingStatisticsPerPeriod() throws NoStatisticsRightsException {
        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalTrainings = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestTrainings, totalTrainings);
    }

    @Test
    public void testGetDurationStatisticsPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalDuration = trainingStatisticsService.getDurationStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestDuration, totalDuration);
    }


    @Test
    public void testGetCaloriesBurnedPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalCaloriesBurned = trainingStatisticsService.getCaloriesBurnedPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestCaloriesBurned, totalCaloriesBurned);
    }


    private TreeMap<Date, TreeSet<Training>> getMockTrainingData() {
        TreeMap<Date, TreeSet<Training>> allTrainings = new TreeMap<>();
        TreeSet<Training> trainings = new TreeSet<>();
        trainings.add(new Training("Test Training 1", TEST_DATE, 60, 200));
        trainings.add(new Training("Test Training 2", TEST_DATE, 45, 150));
        trainings.add(new Training("Test Training 3", TEST_DATE, 90, 300));
        allTrainings.put(TEST_DATE, trainings);
        totalTestTrainings = trainings.size();
        for (Training training : trainings) {
            totalTestDuration += training.getDuration();
            totalTestCaloriesBurned += training.getCaloriesBurned();
        }
        return allTrainings;
    }
}
