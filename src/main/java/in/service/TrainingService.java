package in.service;

import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;

import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingService {
    TreeMap<String, TreeSet<Training>> getAllTrainings(User user);


    TreeSet<Training> getTrainingsByUserEmailAndData(User user, String data) throws RepositoryException;

    Training getTrainingByUserEmailAndDataAndName(User user, String trainingData, String trainingName) throws RepositoryException;

    void saveTraining(User user, Training training) throws RepositoryException, InvalidDateFormatException;

    void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException;

    void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException;

    void changeNameTraining(User user, Training training, String newName) throws RepositoryException;

    void changeDateTraining(User user, Training training, String newDate) throws RepositoryException, InvalidDateFormatException;

    void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException;

    void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException;
}
