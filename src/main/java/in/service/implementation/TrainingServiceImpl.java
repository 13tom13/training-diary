package in.service.implementation;

import in.exception.InvalidDateFormatException;
import in.exception.RepositoryException;
import in.exception.security.rights.NoDeleteRightsException;
import in.exception.security.rights.NoEditRightsException;
import in.exception.security.rights.NoRightsException;
import in.exception.security.rights.NoWriteRightsException;
import in.model.Rights;
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
    public void saveTraining(User user, Training training) throws RepositoryException, InvalidDateFormatException, NoWriteRightsException {
        if (!user.getRights().contains(Rights.WRITE)) {
            throw new NoWriteRightsException();
        }
        if (!isValidDateFormat(training.getDate())) {
            throw new InvalidDateFormatException();
        }
        trainingRepository.saveTraining(user.getEmail(), training);
    }

    @Override
    public void deleteTraining(User user, String date, String name) throws RepositoryException, InvalidDateFormatException, NoDeleteRightsException {
        if (!user.getRights().contains(Rights.DELETE)) {
            throw new NoDeleteRightsException();
        }
        if (isValidDateFormat(date)) {
            Training training = getTrainingByUserEmailAndDataAndName(user, date, name);
            trainingRepository.deleteTraining(user.getEmail(), training);
        } else {
            throw new InvalidDateFormatException();
        }

    }

    @Override
    public void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException, NoWriteRightsException {
        if (!user.getRights().contains(Rights.WRITE)) {
            throw new NoWriteRightsException();
        }
        Training trainingForAdditional = getTrainingForChange(user, training);
        if (!trainingForAdditional.getAdditions().containsKey(additionalName)){
            trainingForAdditional.addAdditional(additionalName, additionalValue);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForAdditional);
        } else {
            throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
        }
    }

    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForRemoval = getTrainingForChange(user, training);
        if (trainingForRemoval.getAdditions().containsKey(additionalName)){
            trainingForRemoval.removeAdditional(additionalName);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForRemoval);
        }
    }

    @Override
    public void changeNameTraining(User user, Training training, String newName) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setName(newName);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    @Override
    public void changeDateTraining(User user, Training training, String newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        if (isValidDateFormat(newDate)) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setDate(newDate);
            trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
        } else {
            throw new InvalidDateFormatException();
        }
    }

    @Override
    public void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setDuration(newDuration);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    @Override
    public void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setCaloriesBurned(newCalories);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    private Training getTrainingForChange(User user, Training training) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user.getEmail(), training.getDate(), training.getName());
    }
}
