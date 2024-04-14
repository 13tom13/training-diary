package in.controller.implementation;

import in.controller.TrainingController;
import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.exception.security.rights.NoDeleteRightsException;
import in.exception.security.rights.NoEditRightsException;
import in.exception.security.rights.NoWriteRightsException;
import in.logger.Logger;
import in.model.Training;
import in.model.User;
import in.service.TrainingService;

import java.util.TreeMap;
import java.util.TreeSet;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    private final Logger logger = Logger.getInstance();

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
            logger.logAction(user.getEmail(), "save training " + training.getName() + " " + training.getDate());

        } catch (RepositoryException | InvalidDateFormatException | NoWriteRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteTraining(User user, String date, String name) {
        try {
            trainingService.deleteTraining(user, date, name);
            logger.logAction(user.getEmail(), "delete training " + name + " " + date);
        } catch (RepositoryException | InvalidDateFormatException | NoDeleteRightsException e) {
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
            logger.logAction(user.getEmail(), String.format("add training additional %s %s (%s %s)",
                    additionalName, additionalValue, training.getName(), training.getDate()));
        } catch (RepositoryException | NoWriteRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) {
        try {
            trainingService.removeTrainingAdditional(user, training, additionalName);
            logger.logAction(user.getEmail(), String.format("remove training additional %s (%s %s)",
                    additionalName, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeNameTraining(User user, Training training, String newName) {
        try {
            trainingService.changeNameTraining(user, training, newName);
            logger.logAction(user.getEmail(),
                    String.format("change training name %s (%s %s)", newName, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeDateTraining(User user, Training training, String newDate) {
        try {
            trainingService.changeDateTraining(user, training, newDate);
            logger.logAction(user.getEmail(),
                    String.format("change training date %s (%s %s)", newDate, training.getName(), training.getDate()));
        } catch (RepositoryException | InvalidDateFormatException | NoEditRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeDurationTraining(User user, Training training, String newDuration) {
        int newDurationInt = Integer.parseInt(newDuration);
        try {
            trainingService.changeDurationTraining(user, training, newDurationInt);
            logger.logAction(user.getEmail(),
                    String.format("change training duration %s (%s %s)", newDuration, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeCaloriesTraining(User user, Training training, String newCalories) {
        int newCaloriesInt = Integer.parseInt(newCalories);
        try {
            trainingService.changeCaloriesTraining(user, training, newCaloriesInt);
            logger.logAction(user.getEmail(),
                    String.format("change training calories %s (%s %s)", newCaloriesInt, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            System.err.println(e.getMessage());
        }
    }
}
