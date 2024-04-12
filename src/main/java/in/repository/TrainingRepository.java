package in.repository;

import in.exception.RepositoryException;
import in.model.Training;

import java.util.TreeSet;

public interface TrainingRepository {
    TreeSet<Training> getAllTrainingsFromUser(String userEmail);

    Training getTrainingByNumber(String userEmail, int trainingNumber) throws RepositoryException;

    Training createTraining(String userEmail, String name, String date, int duration, int caloriesBurned);

    void addTrainingAdditional(Training training, String additionalName, String additionalValue);

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, int newDuration);

    void changeCaloriesTraining(Training training, int newCalories);
}

