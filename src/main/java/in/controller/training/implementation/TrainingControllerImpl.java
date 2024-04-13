package in.controller.training.implementation;

import in.controller.training.TrainingController;
import in.exception.RepositoryException;
import in.model.Training;
import in.service.TrainingService;

import java.util.TreeMap;
import java.util.TreeSet;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainings(String userEmail) {
        return trainingService.getAllTrainings(userEmail);
    }


    @Override
    public void createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) {
        try {
            trainingService.createTraining(userEmail, name, date, duration, caloriesBurned);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String trainingDate) {
        try {
            return trainingService.getTrainingsByUserEmailAndData(userEmail, trainingDate);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return new TreeSet<>();
        }
    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingDate, String trainingName) {
        try {
            return trainingService.getTrainingByUserEmailAndDataAndName(userEmail, trainingDate, trainingName);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return new Training();
        }
    }


    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) {
        try {
            trainingService.addTrainingAdditional(training, additionalName, additionalValue);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeTrainingAdditional(Training training, String additionalName) {
        try {
            trainingService.removeTrainingAdditional(training, additionalName);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void changeNameTraining(Training training, String newName) {
        trainingService.changeNameTraining(training, newName);
    }

    @Override
    public void changeDateTraining(Training training, String newDate) {
        trainingService.changeDateTraining(training, newDate);
    }

    @Override
    public void changeDurationTraining(Training training, String newDuration) {
        int newDurationInt = Integer.parseInt(newDuration);
        trainingService.changeDurationTraining(training, newDurationInt);
    }

    @Override
    public void changeCaloriesTraining(Training training, String newCalories) {
        int newCaloriesInt = Integer.parseInt(newCalories);
        trainingService.changeCaloriesTraining(training, newCaloriesInt);
    }

}
