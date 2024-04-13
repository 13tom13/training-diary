package in.controller.training.implementation;

import in.controller.training.TrainingController;
import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;
import in.service.TrainingService;

import java.util.TreeMap;
import java.util.TreeSet;

import static in.service.implementation.DateValidationService.isValidDateFormat;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainings(User user) {
        return trainingService.getAllTrainings(user);
    }


    @Override
    public void saveTraining(User user, Training training) {
        try {
            trainingService.saveTraining(user, training);
        } catch (RepositoryException | InvalidDateFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(User user, String trainingDate) {
        try {
            return trainingService.getTrainingsByUserEmailAndData(user, trainingDate);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return new TreeSet<>();
        }
    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(User user, String trainingDate, String trainingName) {
        try {
            return trainingService.getTrainingByUserEmailAndDataAndName(user, trainingDate, trainingName);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return new Training();
        }
    }

    @Override
    public void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) {
        try {
            trainingService.addTrainingAdditional(user, training, additionalName, additionalValue);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) {
        try {
            trainingService.removeTrainingAdditional(user, training, additionalName);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeNameTraining(User user, Training training, String newName) {
        try {
            trainingService.changeNameTraining(user, training, newName);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeDateTraining(User user, Training training, String newDate) {
        try {
            trainingService.changeDateTraining(user, training, newDate);
        } catch (RepositoryException | InvalidDateFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeDurationTraining(User user, Training training, String newDuration) {
        int newDurationInt = Integer.parseInt(newDuration);
        try {
            trainingService.changeDurationTraining(user, training, newDurationInt);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeCaloriesTraining(User user, Training training, String newCalories) {
        int newCaloriesInt = Integer.parseInt(newCalories);
        try {
            trainingService.changeCaloriesTraining(user, training, newCaloriesInt);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }
}
