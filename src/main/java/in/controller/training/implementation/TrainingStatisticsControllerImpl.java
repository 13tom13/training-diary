package in.controller.training.implementation;

import in.controller.training.TrainingStatisticsController;
import in.exception.security.rights.NoStatisticsRightsException;
import in.model.User;
import in.service.implementation.TrainingStatisticsService;

public class TrainingStatisticsControllerImpl implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    public TrainingStatisticsControllerImpl(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
    }

    public int getAllTrainingStatistics(User user) {
        try {
            return trainingStatisticsService.getAllTrainingStatistics(user);
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            return trainingStatisticsService.getAllTrainingStatisticsPerPeriod(user, startDate, endDate);
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int getDurationStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            return trainingStatisticsService.getDurationStatisticsPerPeriod(user, startDate, endDate);
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) {
        try {
            return trainingStatisticsService.getCaloriesBurnedPerPeriod(user, startDate, endDate);
        } catch (NoStatisticsRightsException e) {
            throw new RuntimeException(e);
        }
    }
}
