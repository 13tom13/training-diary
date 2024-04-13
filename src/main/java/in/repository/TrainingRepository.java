package in.repository;

import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;

import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingRepository {
    TreeMap<String, TreeSet<Training>> getAllTrainingsByUserEmail(String userEmail);

    TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String trainingData) throws RepositoryException;

    Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingData, String trainingName) throws RepositoryException;

    void saveTraining(String userEmail, Training newTraining) throws RepositoryException;

    void addTrainingAdditional(Training training, String additionalName, String additionalValue) throws RepositoryException;

    void removeTrainingAdditional(Training training, String additionalName) throws RepositoryException;

    void changeNameTraining(Training training, String newName);

    void changeDateTraining(Training training, String newDate);

    void changeDurationTraining(Training training, int newDuration);

    void changeCaloriesTraining(Training training, int newCalories);
}

