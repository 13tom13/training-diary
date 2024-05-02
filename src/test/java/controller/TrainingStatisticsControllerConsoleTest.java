package controller;

import entities.dto.UserDTO;
import in.controller.training.implementation.TrainingStatisticsControllerConsole;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import out.menu.statistic.TriFunction;
import testutil.TestUtil;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
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
    private UserDTO testUser;
    private final LocalDate startDate = LocalDate.of(2024, 1, 1);
    private final LocalDate endDate = LocalDate.of(2024, 12, 31);

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO();
        testUser.setFirstName(TEST_FIRST_NAME);
        testUser.setLastName(TEST_LAST_NAME);
        testUser.setEmail(TEST_EMAIL);
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
        Integer actualStatistics = trainingStatisticsController.getAllTrainingStatisticsPerPeriod(testUser, startDate, endDate);

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
        Integer actualStatistics = trainingStatisticsController.getDurationStatisticsPerPeriod(testUser, startDate, endDate);

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
        Integer actualStatistics = trainingStatisticsController.getCaloriesBurnedPerPeriod(testUser, startDate, endDate);

        // Assert
        assertEquals(expectedStatistics, actualStatistics);
        verify(trainingStatisticsServiceMock).getCaloriesBurnedPerPeriod(testUser, startDate, endDate);
    }
}