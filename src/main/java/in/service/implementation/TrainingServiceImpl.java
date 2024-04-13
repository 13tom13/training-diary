package in.service.implementation;

import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;
import in.repository.TrainingRepository;
import in.service.TrainingService;

import java.util.TreeMap;
import java.util.TreeSet;

import static in.service.implementation.DateValidationService.isValidDateFormat;

public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainings(User user) {
        return trainingRepository.getAllTrainingsByUserEmail(user.getEmail());
    }

    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(User user, String data) throws RepositoryException {
        return trainingRepository.getTrainingsByUserEmailAndData(user.getEmail(), data);
    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(User user, String trainingData, String trainingName) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user.getEmail(), trainingData, trainingName);
    }

    @Override
    public void saveTraining(User user, Training training) throws RepositoryException, InvalidDateFormatException {
        if (isValidDateFormat(training.getDate())) {
            trainingRepository.saveTraining(user.getEmail(), training);
        } else {
            throw new InvalidDateFormatException("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
        }
    }

    @Override
    public void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException {
        Training trainingForAdditional = getTrainingForChange(user, training);
        if (!trainingForAdditional.getAdditions().containsKey(additionalName)){
            trainingForAdditional.addAdditional(additionalName, additionalValue);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForAdditional);
        } else {
            throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
        }
    }

    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException {
        Training trainingForRemoval = getTrainingForChange(user, training);
        if (trainingForRemoval.getAdditions().containsKey(additionalName)){
            trainingForRemoval.removeAdditional(additionalName);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForRemoval);
        }

    }

    @Override
    public void changeNameTraining(User user, Training training, String newName) throws RepositoryException {
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setName(newName);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    @Override
    public void changeDateTraining(User user, Training training, String newDate) throws RepositoryException, InvalidDateFormatException {
        if (isValidDateFormat(newDate)) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setDate(newDate);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
        } else {
            throw new InvalidDateFormatException("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
        }
    }

    @Override
    public void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException {
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setDuration(newDuration);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    @Override
    public void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException {
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setCaloriesBurned(newCalories);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    private Training getTrainingForChange(User user, Training training) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user.getEmail(), training.getDate(), training.getName());
    }
}
