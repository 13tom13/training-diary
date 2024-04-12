package service.implementation;

import exception.RepositoryException;
import model.Training;
import repository.TrainingRepository;
import service.TrainingService;

import java.util.List;
import java.util.TreeSet;

public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TreeSet<Training> getAllTrainings(String userEmail) {
        return trainingRepository.getAllTrainingsFromUser(userEmail);
    }

    @Override
    public Training getTrainingByNumber(String userEmail, int trainingNumber) {
        try {
            return trainingRepository.getTrainingByNumber(userEmail, trainingNumber);
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Training createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) {
        return trainingRepository.createTraining(userEmail, name, date, duration, caloriesBurned);
    }

    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) {
        trainingRepository.addTrainingAdditional(training, additionalName, additionalValue);
    }

    @Override
    public void changeNameTraining(Training training, String newName) {
        trainingRepository.changeNameTraining(training, newName);
    }

    @Override
    public void changeDateTraining(Training training, String newDate) {
        trainingRepository.changeDateTraining(training, newDate);
    }

    @Override
    public void changeDurationTraining(Training training, int newDuration) {
        trainingRepository.changeDurationTraining(training, newDuration);
    }

    @Override
    public void changeCaloriesTraining(Training training, int newCalories) {
        trainingRepository.changeCaloriesTraining(training, newCalories);
    }
}
