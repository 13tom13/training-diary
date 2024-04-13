package in.controller.training;

import in.model.User;

public interface TrainingStatisticsController {
    int getAllTrainingStatistics(User user);

    Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate);

    int getDurationStatisticsPerPeriod(User user, String startDate, String endDate);

    int getCaloriesBurnedPerPeriod(User user, String startDate, String endDate);
}
