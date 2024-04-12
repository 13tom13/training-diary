package service;

import model.Training;

import java.util.TreeSet;

public interface TrainingService {
    TreeSet<Training> getAllTrainings(String userEmail);

    Training getTrainingByNumber(String userEmail, int trainingNumber);

    Training createTraining(String userEmail, String name, String date, int duration, int caloriesBurned);

    void addTrainingAdditional(Training training, String additionalName, String additionalValue);

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, int newDuration);

    void changeCaloriesTraining(Training training, int newCalories);
}
