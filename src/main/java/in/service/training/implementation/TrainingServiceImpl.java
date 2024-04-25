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
    public List<String> getTrainingTypes(User user) {
        return trainingTypeRepository.getTrainingTypes(user);
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
        if (hisRight(user, "WRITE")) {
            trainingRepository.saveTraining(user, training);
        } else {
            throw new NoWriteRightsException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrainingType(User user, String customTrainingType) {
        trainingTypeRepository.saveTrainingType(user, customTrainingType);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean deleteTraining(User user, Date date, String name) throws RepositoryException, InvalidDateFormatException, NoDeleteRightsException {
        if (hisRight(user, "DELETE")) {
            Training training = getTrainingByUserIDAndDataAndName(user, date, name);
            return trainingRepository.deleteTraining(user, training);
        } else {
            throw new NoDeleteRightsException();
        }


    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException, NoWriteRightsException {
        if (hisRight(user, "WRITE")) {
            Training trainingForAdditional = getTrainingForChange(user, training);
            if (!trainingForAdditional.getAdditions().containsKey(additionalName)) {
                trainingForAdditional.addAdditional(additionalName, additionalValue);
                training = trainingRepository.updateTraining(user, training, trainingForAdditional);
            } else {
                throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
            }
        } else {
            throw new NoWriteRightsException();
        }
        return training;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException, NoEditRightsException {
        if (hisRight(user, "EDIT")) {
            Training trainingForRemoval = getTrainingForChange(user, training);
            if (trainingForRemoval.getAdditions().containsKey(additionalName)) {
                trainingForRemoval.removeAdditional(additionalName);
                training = trainingRepository.updateTraining(user, training, trainingForRemoval);
            }
        } else {
            throw new NoEditRightsException();
        }
        return training;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training changeNameTraining(User user, Training training, String newName) throws RepositoryException, NoEditRightsException {
        if (hisRight(user, "EDIT")) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setName(newName);
            training = trainingRepository.updateTraining(user, training, trainingForChange);
        } else {
            throw new NoEditRightsException();
        }
        return training;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training changeDateTraining(User user, Training training, Date newDate) throws RepositoryException, NoEditRightsException {
        if (hisRight(user, "EDIT")) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setDate(newDate);
            training = trainingRepository.updateTraining(user, training, trainingForChange);
        } else {
            throw new NoEditRightsException();
        }
        return training;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException {
        if (hisRight(user, "EDIT")) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setDuration(newDuration);
            training = trainingRepository.updateTraining(user, training, trainingForChange);
        } else {
            throw new NoEditRightsException();
        }
        return training;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Training changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException, NoEditRightsException {
        if (hisRight(user, "EDIT")) {
            Training trainingForChange = getTrainingForChange(user, training);
            trainingForChange.setCaloriesBurned(newCalories);
            training = trainingRepository.updateTraining(user, training, trainingForChange);
        } else {
            throw new NoEditRightsException();
        }
        return training;
    }

    private Training getTrainingForChange(User user, Training training) throws RepositoryException {
        return trainingRepository.getTrainingByUserIDlAndDataAndName(user, training.getDate(), training.getName());
    }

    private boolean hisRight(User user, String rightsName) {
        return user.getRights().stream().anyMatch(rights -> rights.getName().equals(rightsName));
    }
}