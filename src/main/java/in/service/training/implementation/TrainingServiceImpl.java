package in.service.training.implementation;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import entity.model.Training;
import entity.model.User;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.service.training.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.mappers.TrainingMapper;
import utils.mappers.UserMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.getDateFromString;
import static utils.Utils.hisRight;

/**
 * Реализация сервиса для управления тренировками пользователя.
 */
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;

    /**
     * Конструктор.
     *
     * @param trainingRepository     Репозиторий для доступа к тренировкам.
     * @param trainingTypeRepository Репозиторий для доступа к типам тренировок.
     */
    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingTypeRepository trainingTypeRepository, UserMapper userMapper, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.userMapper = userMapper;
        this.trainingMapper = trainingMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        TreeMap<LocalDate, TreeSet<Training>> allUserTrainings = trainingRepository.getAllTrainingsByUserEmail(user);
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTrainingsDTO = new TreeMap<>();

        for (Map.Entry<LocalDate, TreeSet<Training>> entry : allUserTrainings.entrySet()) {
            LocalDate date = entry.getKey();
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
        User user = userMapper.userDTOToUser(userDTO);
        return trainingTypeRepository.getTrainingTypes(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, String date) throws RepositoryException {
        User user = userMapper.userDTOToUser(userDTO);
        LocalDate convertedDate = getDateFromString(date);
        TreeSet<Training> trainingsByUserEmailAndData = trainingRepository.getTrainingsByUserEmailAndData(user, convertedDate);
        return TrainingSetToTrainingDTOSet(trainingsByUserEmailAndData);
    }

    private TreeSet<TrainingDTO> TrainingSetToTrainingDTOSet(TreeSet<Training> trainings) {
        TreeSet<TrainingDTO> trainingDTOs = new TreeSet<>();
        for (Training training : trainings) {
            TrainingDTO trainingDTO = trainingMapper.trainingToTrainingDTO(training);
            trainingDTOs.add(trainingDTO);
        }
        return trainingDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrainingDTO getTrainingByUserEmailAndDataAndName(UserDTO userDTO, String trainingData, String trainingName) throws RepositoryException {
        User user = userMapper.userDTOToUser(userDTO);
        LocalDate convertedDate = getDateFromString(trainingData);
        Training training = trainingRepository.getTrainingByUserEmailAndDataAndName(user, convertedDate, trainingName);
        return trainingMapper.trainingToTrainingDTO(training);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws NoWriteRightsException, RepositoryException {
        if (hisRight(userDTO, "WRITE")) {
            User user = userMapper.userDTOToUser(userDTO);
            Training training = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingFromDB = trainingRepository.saveTraining(user, training);
            return trainingMapper.trainingToTrainingDTO(trainingFromDB);
        } else {
            throw new NoWriteRightsException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrainingType(UserDTO userDTO, String customTrainingType) {
        User user = userMapper.userDTOToUser(userDTO);
        trainingTypeRepository.saveTrainingType(user, customTrainingType);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public void deleteTraining(UserDTO userDTO, String date, String name) throws RepositoryException, NoDeleteRightsException {
        if (hisRight(userDTO, "DELETE")) {
            TrainingDTO trainingDTO = getTrainingByUserEmailAndDataAndName(userDTO, date, name);
            User user = userMapper.userDTOToUser(userDTO);
            Training training = trainingMapper.trainingDTOToTraining(trainingDTO);
            trainingRepository.deleteTraining(user, training);
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
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForAdditional = getTrainingForChange(user, trainingDTO);
            if (!trainingForAdditional.getAdditions().containsKey(additionalName)) {
                trainingForAdditional.addAdditional(additionalName, additionalValue);
                OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForAdditional);
                return trainingMapper.trainingToTrainingDTO(OldTraining);
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
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForRemoval = getTrainingForChange(user, trainingDTO);
            if (trainingForRemoval.getAdditions().containsKey(additionalName)) {
                trainingForRemoval.removeAdditional(additionalName);
                OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForRemoval);
            }
            return trainingMapper.trainingToTrainingDTO(OldTraining);
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
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setName(newName);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return trainingMapper.trainingToTrainingDTO(OldTraining);
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
    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) throws RepositoryException, NoEditRightsException {
        if (hisRight(userDTO, "EDIT")) {
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setDate(newDate);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return trainingMapper.trainingToTrainingDTO(OldTraining);
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
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setDuration(newDuration);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return trainingMapper.trainingToTrainingDTO(OldTraining);
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
            User user = userMapper.userDTOToUser(userDTO);
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            trainingForChange.setCaloriesBurned(newCalories);
            OldTraining = trainingRepository.updateTraining(user, OldTraining, trainingForChange);
            return trainingMapper.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

    private Training getTrainingForChange(User user, TrainingDTO trainingDTO) throws RepositoryException {
        return trainingRepository.getTrainingByUserEmailAndDataAndName(user, trainingDTO.getDate(), trainingDTO.getName());
    }

}