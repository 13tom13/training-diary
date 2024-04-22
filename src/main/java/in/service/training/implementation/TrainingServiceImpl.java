package in.service.training.implementation;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.service.training.TrainingService;
import model.Training;
import model.User;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация сервиса для управления тренировками пользователя.
 */
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Конструктор.
     *
     * @param trainingRepository     Репозиторий для доступа к тренировкам.
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
    public TreeMap<Date, TreeSet<Training>> getAllTrainings(User user) {
        return trainingRepository.getAllTrainingsByUserID(user);
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
    public TreeSet<Training> getTrainingsByUserIDAndData(User user, Date data) throws RepositoryException {
        return trainingRepository.getTrainingsByUserIDAndData(user, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Training getTrainingByUserIDAndDataAndName(User user, Date trainingData, String trainingName) throws RepositoryException {
        return trainingRepository.getTrainingByUserIDlAndDataAndName(user, trainingData, trainingName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTraining(User user, Training training) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        if (!hisRight(user, "WRITE")) {
            throw new NoWriteRightsException();
        }
//        if (!isValidDateFormat(training.getDate())) {
//            throw new InvalidDateFormatException();
//        }
        trainingRepository.saveTraining(user, training);
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
     *
     * @return
     */
    @Override
    public boolean deleteTraining(User user, Date date, String name) throws RepositoryException, InvalidDateFormatException, NoDeleteRightsException {
        if (!hisRight(user, "DELETE")) {
            throw new NoDeleteRightsException();
        }
        Training training = getTrainingByUserIDAndDataAndName(user, date, name);
        return trainingRepository.deleteTraining(user, training);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException, NoWriteRightsException {
        if (!hisRight(user, "WRITE")) {
            throw new NoWriteRightsException();
        }
        Training trainingForAdditional = getTrainingForChange(user, training);
        if (!trainingForAdditional.getAdditions().containsKey(additionalName)) {
            trainingForAdditional.addAdditional(additionalName, additionalValue);
            trainingRepository.updateTraining(user, training, trainingForAdditional);
        } else {
            throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException, NoEditRightsException {
        if (!hisRight(user, "EDIT")) {
            throw new NoEditRightsException();
        }
        Training trainingForRemoval = getTrainingForChange(user, training);
        if (trainingForRemoval.getAdditions().containsKey(additionalName)) {
            trainingForRemoval.removeAdditional(additionalName);
            trainingRepository.updateTraining(user, training, trainingForRemoval);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeNameTraining(User user, Training training, String newName) throws RepositoryException, NoEditRightsException {
        if (!hisRight(user, "EDIT")) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setName(newName);
        trainingRepository.updateTraining(user, training, trainingForChange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDateTraining(User user, Training training, Date newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException {
        if (!hisRight(user, "EDIT")) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setDate(newDate);
        trainingRepository.updateTraining(user, training, trainingForChange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException {
        if (!hisRight(user, "EDIT")) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setDuration(newDuration);
        trainingRepository.updateTraining(user, training, trainingForChange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException, NoEditRightsException {
        if (!hisRight(user, "EDIT")) {
            throw new NoEditRightsException();
        }
        Training trainingForChange = getTrainingForChange(user, training);
        trainingForChange.setCaloriesBurned(newCalories);
        trainingRepository.updateTraining(user, training, trainingForChange);
    }

    private Training getTrainingForChange(User user, Training training) throws RepositoryException {
        return trainingRepository.getTrainingByUserIDlAndDataAndName(user, training.getDate(), training.getName());
    }

    private boolean hisRight(User user, String rightsName) {
        return user.getRights().stream().anyMatch(rights -> rights.getName().equals(rightsName));
    }
}