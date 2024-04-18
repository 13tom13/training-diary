package controller;

import in.controller.training.implementation.TrainingStatisticsControllerImpl;
import exceptions.security.rights.NoStatisticsRightsException;
import model.User;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование класса TrainingStatisticsControllerImpl.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TrainingStatisticsControllerImplTest extends TestUtil {

    @Mock
    private TrainingStatisticsServiceImp trainingStatisticsServiceMock;

    private TrainingStatisticsControllerImpl trainingStatisticsController;
    private User testUser;
    private String startDate;
    private String endDate;

    @BeforeEach
    public void setUp() {

        trainingStatisticsController = new TrainingStatisticsControllerImpl(trainingStatisticsServiceMock);
        testUser = new User("John", "Doe", TEST_EMAIL, "password");
        startDate = "01.01.24";
        endDate = "31.01.24";
    }

    /**
     * Тестирование получения общей статистики тренировок.
     *
     * @throws NoStatisticsRightsException если нет прав на просмотр статистики
     */
    @Test
    public void testGetAllTrainingStatistics() throws NoStatisticsRightsException {
        // Arrange
        int expectedStatistics = 100;
        when(trainingStatisticsServiceMock.getAllTrainingStatistics(testUser)).thenReturn(expectedStatistics);

        // Act
        int actualStatistics = trainingStatisticsController.getAllTrainingStatistics(testUser);

        // Assert
        assertEquals(expectedStatistics, actualStatistics);
        verify(trainingStatisticsServiceMock).getAllTrainingStatistics(testUser);
    }

    /**
     * Тестирование получения общей статистики тренировок за определенный период.
     *
     * @throws NoStatisticsRightsException если нет прав на просмотр статистики
     */
    @Test
    public void testGetAllTrainingStatisticsPerPeriod() throws NoStatisticsRightsException {
        // Arrange
        int expectedStatistics = 50;
        when(trainingStatisticsServiceMock.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate))
                .thenReturn(expectedStatistics);

        // Act
        int actualStatistics = trainingStatisticsController.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(expectedStatistics, actualStatistics);
        verify(trainingStatisticsServiceMock).getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);
    }

    /**
     * Тестирование получения статистики по продолжительности тренировок за определенный период.
     *
     * @throws NoStatisticsRightsException если нет прав на просмотр статистики
     */
    @Test
    public void testGetDurationStatisticsPerPeriod() throws NoStatisticsRightsException {
        // Arrange
        int expectedStatistics = 1200;
        when(trainingStatisticsServiceMock.getDurationStatisticsPerPeriod(testUser, startDate, endDate))
                .thenReturn(expectedStatistics);

        // Act
        int actualStatistics = trainingStatisticsController.getDurationStatisticsPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(expectedStatistics, actualStatistics);
        verify(trainingStatisticsServiceMock).getDurationStatisticsPerPeriod(testUser, startDate, endDate);
    }

    /**
     * Тестирование получения статистики по сожженным калориям за определенный период.
     *
     * @throws NoStatisticsRightsException если нет прав на просмотр статистики
     */
    @Test
    public void testGetCaloriesBurnedPerPeriod() throws NoStatisticsRightsException {
        // Arrange
        int expectedStatistics = 5000;
        when(trainingStatisticsServiceMock.getCaloriesBurnedPerPeriod(testUser, startDate, endDate))
                .thenReturn(expectedStatistics);

        // Act
        int actualStatistics = trainingStatisticsController.getCaloriesBurnedPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(expectedStatistics, actualStatistics);
        verify(trainingStatisticsServiceMock).getCaloriesBurnedPerPeriod(testUser, startDate, endDate);
    }
}
