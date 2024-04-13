package in.service.implementation;

import in.exception.RepositoryException;
import in.model.Training;
import in.repository.TrainingRepository;
import in.service.TrainingService;

import java.util.TreeMap;
import java.util.TreeSet;

public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainings(String userEmail) {
       return trainingRepository.getAllTrainingsByUserEmail(userEmail);
    }

    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String data) throws RepositoryException {
        return trainingRepository.getTrainingsByUserEmailAndData(userEmail, data);

    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingData, String trainingName) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(userEmail, trainingData, trainingName);
    }


    @Override
    public void createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) throws RepositoryException {
        Training newTraining = new Training(name, date, duration, caloriesBurned);
        trainingRepository.saveTraining(userEmail, newTraining);
    }

    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) throws RepositoryException {
        trainingRepository.addTrainingAdditional(training, additionalName, additionalValue);
    }

    @Override
    public void removeTrainingAdditional(Training training, String additionalName) throws RepositoryException {
        trainingRepository.removeTrainingAdditional(training, additionalName);
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
