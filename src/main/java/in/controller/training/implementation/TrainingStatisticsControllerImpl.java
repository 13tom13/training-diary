package in.controller.training.implementation;

import in.controller.training.TrainingStatisticsController;
import exceptions.security.rights.NoStatisticsRightsException;
import utils.Logger;
import model.User;
import in.service.training.TrainingStatisticsService;

public class TrainingStatisticsControllerImpl implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    private static Logger logger;

    public TrainingStatisticsControllerImpl(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
        logger = Logger.getInstance();
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
