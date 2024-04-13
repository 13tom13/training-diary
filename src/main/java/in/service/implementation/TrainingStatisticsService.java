package in.service.implementation;

import in.model.Training;
import in.model.User;
import in.service.TrainingService;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class TrainingStatisticsService implements in.service.TrainingStatisticsService {

    private final TrainingService trainingService;

    public TrainingStatisticsService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public int getAllTrainingStatistics(User user) {
        TreeMap<String, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(user);
        int totalTrainings = 0;
        for (TreeSet<Training> trainingsOnDate : allTrainings.values()) {
            totalTrainings += trainingsOnDate.size();
        }
        return totalTrainings;
    }

    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) {
        int totalTrainings;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        totalTrainings = trainings.size();

        return totalTrainings;
    }

    public Integer getDurationStatisticsPerPeriod(User user, String startDate, String endDate) {
        int totalDuration = 0;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        for (Training training : trainings) {
            totalDuration += training.getDuration();
        }
        return totalDuration;
    }

    public Integer getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) {
        int totalCaloriesBurned = 0;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        for (Training training : trainings) {
            totalCaloriesBurned += training.getCaloriesBurned();
        }
        return totalCaloriesBurned;
    }

    private List<Training> getTrainingsInPeriod(User user, String startDate, String endDate) {
        TreeMap<String, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(user);
        List<Training> trainingsInPeriod = new ArrayList<>();
        for (TreeSet<Training> trainingsOnDate : allTrainings.values()) {
            for (Training training : trainingsOnDate) {
                if (isTrainingInPeriod(training, startDate, endDate)) {
                    trainingsInPeriod.add(training);
                }
            }
        }
        return trainingsInPeriod;
    }

    private boolean isTrainingInPeriod(Training training, String startDate, String endDate) {
        String trainingDate = training.getDate();
        return trainingDate.compareTo(startDate) >= 0 && trainingDate.compareTo(endDate) <= 0;
    }

}

