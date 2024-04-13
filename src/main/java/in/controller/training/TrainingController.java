package in.controller.training;

import in.model.Training;
import in.model.User;

import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingController {

    TreeMap<String, TreeSet<Training>> getAllTrainings(User user);

    void saveTraining(User user, Training training);

    void deleteTraining(User user, String date, String name);

    TreeSet<Training> getTrainingsByUserEmailAndData(User user, String trainingDate);

    Training getTrainingByUserEmailAndDataAndName(User user, String trainingDate, String trainingName);

    void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue);

    void removeTrainingAdditional(User user, Training training, String additionalName);

    void changeNameTraining(User user, Training training, String newName);

    void changeDateTraining(User user, Training training, String newDate);

    void changeDurationTraining(User user, Training training, String newDuration);

    void changeCaloriesTraining(User user, Training training, String newCalories);
}

