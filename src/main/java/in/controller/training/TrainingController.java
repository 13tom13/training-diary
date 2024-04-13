package in.controller.training;

import in.model.Training;
import in.model.User;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingController {

    TreeMap<String, TreeSet<Training>> getAllTrainings (String userEmail);


    void createTraining (String userEmail, String name, String date, int duration, int caloriesBurned);


    TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String trainingDate);

    Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingDate, String trainingName);

    void addTrainingAdditional(Training training, String additionalName, String additionalValue);

    void removeTrainingAdditional(Training training, String additionalName);

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, String newDuration);

    void changeCaloriesTraining(Training training, String newCalories);

}
