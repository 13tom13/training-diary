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
import in.repository.user.UserRepository;
import in.service.training.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.mappers.TrainingMapper;
import utils.mappers.UserMapper;

import java.time.LocalDate;
import java.util.*;

import static utils.Utils.getDateFromString;
import static utils.Utils.hisRight;

/**
 * Реализация сервиса для управления тренировками пользователя.
 */
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;

    /**
     * Конструктор.
     *
     * @param trainingRepository     Репозиторий для доступа к тренировкам.
     * @param trainingTypeRepository Репозиторий для доступа к типам тренировок.
     */
    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingTypeRepository trainingTypeRepository, UserRepository userRepository, UserMapper userMapper, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.trainingMapper = trainingMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(String email) {
//        User user = userMapper.userDTOToUser(userDTO);
        TreeMap<LocalDate, TreeSet<Training>> allUserTrainings = trainingRepository.getAllTrainingsByUserEmail(email);
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
    public List<String> getTrainingTypes(long id) {
        return trainingTypeRepository.getTrainingTypes(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(String email, String date) throws RepositoryException {
//        User user = userMapper.userDTOToUser(userDTO);
        LocalDate convertedDate = getDateFromString(date);
        TreeSet<Training> trainingsByUserEmailAndData = trainingRepository.getTrainingsByUserEmailAndData(email, convertedDate);
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
    public TrainingDTO getTrainingByUserEmailAndDataAndName(String email, String trainingData, String trainingName) throws RepositoryException {
        LocalDate convertedDate = getDateFromString(trainingData);
        Training training = trainingRepository.getTrainingByUserEmailAndDataAndName(email, convertedDate, trainingName);
        return trainingMapper.trainingToTrainingDTO(training);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws NoWriteRightsException, RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isEmpty()) {
            throw new RepositoryException("Пользователь не найден");
        } else {
            User user = userByEmail.get();
            if (hisRight(user.getRights(), "WRITE")) {
                Training training = trainingMapper.trainingDTOToTraining(trainingDTO);
                Training trainingFromDB = trainingRepository.saveTraining(user, training);
                return trainingMapper.trainingToTrainingDTO(trainingFromDB);
            } else {
                throw new NoWriteRightsException();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrainingType(long id, String customTrainingType) {
        System.out.println("userDTO id from saveTrainingType: " + id);
        trainingTypeRepository.saveTrainingType(id, customTrainingType);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public void deleteTraining(String email, String date, String name) throws RepositoryException, NoDeleteRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "DELETE")) {
            TrainingDTO trainingDTO = getTrainingByUserEmailAndDataAndName(email, date, name);
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
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        if (hisRight(user.getRights(), "WRITE")) {
            Training oldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
//            HashMap<String, String> trainingAdditions = trainingRepository.getTrainingAdditions(oldTraining.getId());
//            oldTraining.setAdditions(trainingAdditions);
            if (oldTraining.getAdditions() == null || oldTraining.getAdditions().isEmpty()) {
                oldTraining.setAdditions(new HashMap<>());
            }
            System.out.println("additional from trainingDTO: " + oldTraining.getAdditions());
            if (!oldTraining.getAdditions().containsKey(additionalName)) {
                oldTraining.addAdditional(additionalName, additionalValue);
                oldTraining = trainingRepository.updateTraining(user, oldTraining);
                return trainingMapper.trainingToTrainingDTO(oldTraining);
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
    public TrainingDTO removeTrainingAdditional(String email, TrainingDTO trainingDTO, String additionalName) throws RepositoryException, NoEditRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "EDIT")) {
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            HashMap<String, String> trainingAdditions = trainingRepository.getTrainingAdditions(trainingDTO.getId());
            if (OldTraining.getAdditions() == null || trainingDTO.getAdditions().isEmpty()) {
                System.err.println("Cписок дополнений пуст");
                return trainingDTO;
            }
            if (trainingAdditions.containsKey(additionalName)) {
                OldTraining.removeAdditional(additionalName);
                OldTraining = trainingRepository.updateTraining(user, OldTraining);
            } else {
                System.err.println("Дополнительное поле с именем " + additionalName + " не существует");
                return trainingDTO;
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
    public TrainingDTO changeNameTraining(TrainingDTO trainingDTO, String email, String newName) throws RepositoryException, NoEditRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "EDIT")) {
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            OldTraining.setName(newName);
            OldTraining = trainingRepository.updateTraining(user, OldTraining);
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
    public TrainingDTO changeDateTraining(TrainingDTO trainingDTO, String email, LocalDate newDate) throws RepositoryException, NoEditRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "EDIT")) {
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            OldTraining.setDate(newDate);
            OldTraining = trainingRepository.updateTraining(user, OldTraining);
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
    public TrainingDTO changeDurationTraining(TrainingDTO trainingDTO, String email, int newDuration) throws RepositoryException, NoEditRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "EDIT")) {
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
            OldTraining.setDuration(newDuration);
            OldTraining = trainingRepository.updateTraining(user, OldTraining);
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
    public TrainingDTO changeCaloriesTraining(TrainingDTO trainingDTO, String email, int newCalories) throws RepositoryException, NoEditRightsException {
        User user = userRepository.getUserByEmail(email).get();
        if (hisRight(user.getRights(), "EDIT")) {
            Training OldTraining = trainingMapper.trainingDTOToTraining(trainingDTO);
//            Training trainingForChange = getTrainingForChange(user, trainingDTO);
            OldTraining.setCaloriesBurned(newCalories);
            OldTraining = trainingRepository.updateTraining(user, OldTraining);
            return trainingMapper.trainingToTrainingDTO(OldTraining);
        } else {
            throw new NoEditRightsException();
        }
    }

//    private Training getTrainingForChange(User user, TrainingDTO trainingDTO) throws RepositoryException {
//        return trainingRepository.getTrainingByUserEmailAndDataAndName(user.getEmail(),
//                trainingDTO.getDate(), trainingDTO.getName());
//    }

}