package in.controller.implementation;

import in.controller.TrainingStatisticsController;
import in.exception.security.rights.NoStatisticsRightsException;
import in.logger.Logger;
import in.model.User;
import in.service.implementation.TrainingStatisticsService;

public class TrainingStatisticsControllerImpl implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    private static final Logger logger = Logger.getInstance();

    public TrainingStatisticsControllerImpl(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
    }

    public int getAllTrainingStatistics(User user) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatistics(user);
            logger.logAction(user.getEmail(), "get all training statistics");
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get all training statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int getDurationStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getDurationStatisticsPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get duration statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getCaloriesBurnedPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get calories burned statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            throw new RuntimeException(e);
        }
    }
}
