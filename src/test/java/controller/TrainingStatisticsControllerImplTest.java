package controller;

import in.controller.implementation.TrainingStatisticsControllerImpl;
import in.exception.security.rights.NoStatisticsRightsException;
import in.model.User;
import in.service.implementation.TrainingStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingStatisticsControllerImplTest extends TestUtil {

    @Mock
    private TrainingStatisticsService trainingStatisticsServiceMock;

    private TrainingStatisticsControllerImpl trainingStatisticsController;
    private User testUser;
    private String startDate;
    private String endDate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingStatisticsController = new TrainingStatisticsControllerImpl(trainingStatisticsServiceMock);
        testUser = new User("John", "Doe", TEST_EMAIL, "password");
        startDate = "01.01.24";
        endDate = "31.01.24";
    }


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
