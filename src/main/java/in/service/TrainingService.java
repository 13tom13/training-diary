package in.service;

import in.exception.RepositoryException;
import in.model.Training;

import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingService {
    TreeMap<String, TreeSet<Training>> getAllTrainings(String userEmail);


    TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String data) throws RepositoryException;

    Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingData, String trainingName) throws RepositoryException;

    void createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) throws RepositoryException;

    void addTrainingAdditional(Training training, String additionalName, String additionalValue) throws RepositoryException;

    void removeTrainingAdditional(Training training, String additionalName) throws RepositoryException;

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, int newDuration);

    void changeCaloriesTraining(Training training, int newCalories);
}
