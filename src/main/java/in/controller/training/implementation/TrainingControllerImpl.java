package in.controller.training.implementation;

import in.controller.training.TrainingController;
import in.model.Training;
import in.model.User;
import in.service.TrainingService;

import java.util.List;
import java.util.TreeSet;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public TreeSet<Training> getAllTrainings(String userEmail) {
        return trainingService.getAllTrainings(userEmail);
    }

    @Override
    public Training getTrainingByNumber(String userEmail, int trainingNumber) {
        return null;
    }

    @Override
    public Training createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) {
        return trainingService.createTraining(userEmail, name, date, duration, caloriesBurned);
    }

    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) {
        trainingService.addTrainingAdditional(training, additionalName, additionalValue);

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

    @Override
    public List<Training> getTrainingsByUserAndDay(User user, String trainingDate) {
        // TODO: реализовать метод getTrainingsByUserAndDay
        return null;
    }
}
