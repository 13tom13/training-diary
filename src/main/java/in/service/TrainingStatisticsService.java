package in.service;

import in.model.User;

public interface TrainingStatisticsService {

    int getAllTrainingStatistics(User user);

    Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate);

    Integer getDurationStatisticsPerPeriod(User user, String startDate, String endDate);

    Integer getCaloriesBurnedPerPeriod(User user, String startDate, String endDate);
}
