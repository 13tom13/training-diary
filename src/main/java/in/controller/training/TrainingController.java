package in.controller.training;

import in.model.Training;
import in.model.User;

import java.util.List;
import java.util.TreeSet;

public interface TrainingController {

    TreeSet<Training> getAllTrainings (String userEmail);

    Training getTrainingByNumber(String userEmail, int trainingNumber);

    Training createTraining (String userEmail, String name, String date, int duration, int caloriesBurned);

    void addTrainingAdditional(Training training, String additionalName, String additionalValue);

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, String newDuration);

    void changeCaloriesTraining(Training training, String newCalories);

    List<Training> getTrainingsByUserAndDay(User user, String trainingDate);
}