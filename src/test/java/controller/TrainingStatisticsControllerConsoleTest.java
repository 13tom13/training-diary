package controller;

import entities.model.User;
import in.controller.training.implementation.TrainingStatisticsControllerConsole;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingStatisticsControllerConsoleTest extends TestUtil {

    @Mock
    private TrainingStatisticsServiceImp trainingStatisticsServiceMock;

    @InjectMocks
    private TrainingStatisticsControllerConsole trainingStatisticsController;
    private User testUser;
    private final Date startDate = new Date(2024, 1, 1);
    private final Date endDate = new Date(2024, 12, 31);

    @BeforeEach
    public void setUp() {
        testUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PASSWORD);
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