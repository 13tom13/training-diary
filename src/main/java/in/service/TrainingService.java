package in.service;

import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.exception.security.rights.NoDeleteRightsException;
import in.exception.security.rights.NoEditRightsException;
import in.exception.security.rights.NoWriteRightsException;
import in.model.Training;
import in.model.User;

import java.util.TreeMap;
import java.util.TreeSet;

public interface TrainingService {
    TreeMap<String, TreeSet<Training>> getAllTrainings(User user) throws SecurityException;


    TreeSet<Training> getTrainingsByUserEmailAndData(User user, String data) throws RepositoryException, SecurityException;

    Training getTrainingByUserEmailAndDataAndName(User user, String trainingData, String trainingName) throws RepositoryException, SecurityException;

    void saveTraining(User user, Training training) throws RepositoryException, InvalidDateFormatException, NoWriteRightsException;

    void deleteTraining(User user, String date, String name) throws RepositoryException, InvalidDateFormatException, SecurityException, NoDeleteRightsException;

    void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException, SecurityException, NoWriteRightsException;

    void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException, SecurityException, NoEditRightsException;

    void changeNameTraining(User user, Training training, String newName) throws RepositoryException, SecurityException, NoEditRightsException;

    void changeDateTraining(User user, Training training, String newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException;

    void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException;

    void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException, NoEditRightsException;

}
