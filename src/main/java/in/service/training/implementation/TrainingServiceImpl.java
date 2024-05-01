package in.service.training.implementation;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import entities.model.Training;
import entities.model.User;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.service.training.TrainingService;
import servlet.mappers.TrainingMapper;
import servlet.mappers.UserMapper;

import java.util.*;

import static utils.Utils.hisRight;

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
    public TreeMap<Date, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        TreeMap<Date, TreeSet<Training>> allUserTrainings = trainingRepository.getAllTrainingsByUserEmail(user);
        TreeMap<Date, TreeSet<TrainingDTO>> allTrainingsDTO = new TreeMap<>();

        for (Map.Entry<Date, TreeSet<Training>> entry : allUserTrainings.entrySet()) {
            Date date = entry.getKey();
            TreeSet<Training> trainings = entry.getValue();
            TreeSet<TrainingDTO> trainingDTOs = TrainingSetToTrainingDTOSet(trainings);
            allTrainingsDTO.put(date, trainingDTOs);
        }

        return allTrainingsDTO;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTrainingTypes(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        return trainingTypeRepository.getTrainingTypes(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, Date data) throws RepositoryException {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        TreeSet<Training> trainingsByUserEmailAndData = trainingRepository.getTrainingsByUserEmailAndData(user, data);
        return TrainingSetToTrainingDTOSet(trainingsByUserEmailAndData);
    }

    private static TreeSet<TrainingDTO> TrainingSetToTrainingDTOSet(TreeSet<Training> trainings) {
        TreeSet<TrainingDTO> trainingDTOs = new TreeSet<>();
        for (Training training : trainings) {
            TrainingDTO trainingDTO = TrainingMapper.INSTANCE.trainingToTrainingDTO(training);
            trainingDTOs.add(trainingDTO);
        }
        return trainingDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrainingDTO getTrainingByUserEmailAndDataAndName(UserDTO userDTO, Date trainingData, String trainingName) throws RepositoryException {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        Training training = trainingRepository.getTrainingByUserEmailAndDataAndName(user, trainingData, trainingName);
        return TrainingMapper.INSTANCE.trainingToTrainingDTO(training);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        if (hisRight(userDTO, "WRITE")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training training = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingFromDB = trainingRepository.saveTraining(user, training);
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(trainingFromDB);
        } else {
            throw new NoWriteRightsException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrainingType(UserDTO userDTO, String customTrainingType) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        trainingTypeRepository.saveTrainingType(user, customTrainingType);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean deleteTraining(UserDTO userDTO, Date date, String name) throws RepositoryException, InvalidDateFormatException, NoDeleteRightsException {
        if (hisRight(userDTO, "DELETE")) {
            TrainingDTO trainingDTO = getTrainingByUserEmailAndDataAndName(userDTO, date, name);
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training training = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
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
    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) throws RepositoryException, NoWriteRightsException {

        if (hisRight(userDTO, "WRITE")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForAdditional = getTrainingForChange(user, trainingDTO);
            if (!trainingForAdditional.getAdditions().containsKey(additionalName)) {
                trainingForAdditional.addAdditional(additionalName, additionalValue);
                OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForAdditional);
                return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
            } else {
                throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
            }
        } else {
            throw new NoWriteRightsException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForRemoval = getTrainingForChange(user, trainingDTO);
            if (trainingForRemoval.getAdditions().containsKey(additionalName)) {
                trainingForRemoval.removeAdditional(additionalName);
                OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForRemoval);
            }
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setName(newName);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, Date newDate) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setDate(newDate);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newDuration) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setDuration(newDuration);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newCalories) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            Training OldTraining = TrainingMapper.INSTANCE.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setCaloriesBurned(newCalories);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return TrainingMapper.INSTANCE.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    private Training getTrainingForChange(User user, TrainingDTO trainingDTO) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user, trainingDTO.getDate(), trainingDTO.getName());
    }

}