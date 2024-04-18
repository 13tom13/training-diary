package in.service.training.implementation;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import model.Rights;
import model.Training;
import model.User;
import in.repository.TrainingRepository;
import in.repository.TrainingTypeRepository;
import in.service.training.TrainingService;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.DateValidationService.isValidDateFormat;

/**
 * Реализация сервиса для управления тренировками пользователя.
 */
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Конструктор.
     *
     * @param trainingRepository    Репозиторий для доступа к тренировкам.
     * @param trainingTypeRepository Репозиторий для доступа к типам тренировок.
     */
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingTypeRepository trainingTypeRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainings(User user) {
        return trainingRepository.getAllTrainingsByUserEmail(user.getEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTrainingTypes(String userEmail) {
        return trainingTypeRepository.getTrainingTypes(userEmail);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(User user, String data) throws RepositoryException {
        return trainingRepository.getTrainingsByUserEmailAndData(user.getEmail(), data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Training getTrainingByUserEmailAndDataAndName(User user, String trainingData, String trainingName) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user.getEmail(), trainingData, trainingName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTraining(User user, Training training) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        if (!user.getRights().contains(Rights.WRITE)) {
            throw new NoWriteRightsException();
        }
        if (!isValidDateFormat(training.getDate())) {
            throw new InvalidDateFormatException();
        }
        trainingRepository.saveTraining(user.getEmail(), training);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrainingType(User user, String customTrainingType) {
        trainingTypeRepository.saveTrainingType(user.getEmail(), customTrainingType);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeNameTraining(User user, Training training, String newName) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setName(newName);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException {
        if (!user.getRights().contains(Rights.EDIT)) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setDuration(newDuration);
        trainingRepository.updateTraining(user.getEmail(), training, trainingForChange);
    }

    /**
     * {@inheritDoc}
     */
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
