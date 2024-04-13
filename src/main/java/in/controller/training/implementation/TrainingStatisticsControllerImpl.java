package in.controller.training.implementation;

import in.controller.training.TrainingStatisticsController;
import in.model.User;
import in.service.implementation.TrainingStatisticsService;

public class TrainingStatisticsControllerImpl implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    public TrainingStatisticsControllerImpl(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
    }

    public int getAllTrainingStatistics(User user) {
        return trainingStatisticsService.getAllTrainingStatistics(user);
    }

    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) {
        return trainingStatisticsService.getAllTrainingStatisticsPerPeriod(user, startDate, endDate);
    }

    public int getDurationStatisticsPerPeriod(User user, String startDate, String endDate) {
        return trainingStatisticsService.getDurationStatisticsPerPeriod(user, startDate, endDate);
    }

    public int getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) {
        return trainingStatisticsService.getCaloriesBurnedPerPeriod(user, startDate, endDate);
    }
}
