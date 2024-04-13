package in.service;

import in.exception.security.rights.NoStatisticsRightsException;
import in.model.User;

public interface TrainingStatisticsService {

    int getAllTrainingStatistics(User user) throws NoStatisticsRightsException;

    Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;

    Integer getDurationStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;

    Integer getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;
}
