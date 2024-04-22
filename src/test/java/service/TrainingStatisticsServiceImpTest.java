package service;

import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingService;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import model.Rights;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Тестирование класса TrainingStatisticsServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class TrainingStatisticsServiceImpTest extends TestUtil {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingStatisticsServiceImp trainingStatisticsService;

    private User testUser;
    private final Date startDate = "01.01.24";
    private final Date endDate = "31.01.24";

    private int totalTestTrainings;

    private int totalTestDuration;

    private int totalTestCaloriesBurned;

    @BeforeEach
    public void setUp() {
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
        testUser.getRights().add(Rights.STATISTICS);
    }

    /**
     * Тестирование метода getAllTrainingStatistics().
     *
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Test
    public void testGetAllTrainingStatistics() throws NoStatisticsRightsException {
        // Arrange
        TreeMap<String, TreeSet<Training>> allTrainings = new TreeMap<>();
        allTrainings.put("01.01.24", new TreeSet<>());
        allTrainings.put("02.01.24", new TreeSet<>());

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(allTrainings);

        // Act
        int totalTrainings = trainingStatisticsService.getAllTrainingStatistics(testUser);

        // Assert
        assertEquals(0, totalTrainings);
    }

    /**
     * Тестирование метода getAllTrainingStatisticsPerPeriod().
     *
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Test
    public void testGetAllTrainingStatisticsPerPeriod() throws NoStatisticsRightsException {
        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalTrainings = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestTrainings, totalTrainings);
    }

    /**
     * Тестирование метода getDurationStatisticsPerPeriod().
     *
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Test
    public void testGetDurationStatisticsPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalDuration = trainingStatisticsService.getDurationStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestDuration, totalDuration);
    }

    /**
     * Тестирование метода getCaloriesBurnedPerPeriod().
     *
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Test
    public void testGetCaloriesBurnedPerPeriod() throws NoStatisticsRightsException {

        // Configure mock behavior
        when(trainingService.getAllTrainings(testUser)).thenReturn(getMockTrainingData());

        // Act
        int totalCaloriesBurned = trainingStatisticsService.getCaloriesBurnedPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(totalTestCaloriesBurned, totalCaloriesBurned);
    }

    /**
     * Метод для создания тестовых данных тренировок.
     *
     * @return TreeMap<String, TreeSet < Training>> - структура данных для тестов
     */
    private TreeMap<String, TreeSet<Training>> getMockTrainingData() {
        TreeMap<String, TreeSet<Training>> allTrainings = new TreeMap<>();
        TreeSet<Training> trainings = new TreeSet<>();
        trainings.add(new Training("Test Training 1", "01.01.24", 60, 200));
        trainings.add(new Training("Test Training 2", "01.01.24", 45, 150));
        trainings.add(new Training("Test Training 3", "01.01.24", 90, 300));
        allTrainings.put(TEST_EMAIL, trainings);
        totalTestTrainings = trainings.size();
        for (Training training : trainings) {
            totalTestDuration += training.getDuration();
            totalTestCaloriesBurned += training.getCaloriesBurned();
        }
        return allTrainings;
    }
}
