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

    void deleteTraining(String userEmail, Training training) throws RepositoryException;

    void updateTraining(String userEmail, Training oldTraining, Training newTraining) throws RepositoryException;


}

