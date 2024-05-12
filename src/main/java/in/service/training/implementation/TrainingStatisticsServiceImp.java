package in.service.training.implementation;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import entity.model.User;
import exceptions.security.rights.NoStatisticsRightsException;
import in.repository.user.UserRepository;
import in.service.training.TrainingService;
import in.service.training.TrainingStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static utils.Utils.hisRight;

/**
 * Реализация сервиса статистики тренировок.
 */
@Service
public class TrainingStatisticsServiceImp implements TrainingStatisticsService {

    private final TrainingService trainingService;
    private final UserRepository userRepository;

    /**
     * Создает новый экземпляр TrainingStatisticsServiceImpl.
     *
     * @param trainingService сервис тренировок, используемый для получения информации о тренировках
     */
    @Autowired
    public TrainingStatisticsServiceImp(TrainingService trainingService, UserRepository userRepository) {
        this.trainingService = trainingService;
        this.userRepository = userRepository;
    }


    /**
     * Получает общее количество тренировок пользователя.
     *
     * @param userDTO пользователь, для которого нужно получить статистику
     * @return общее количество тренировок
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public Integer getAllTrainingStatistics(UserDTO userDTO) throws NoStatisticsRightsException {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        if (hisRight(user.getRights(), "STATISTICS")) {
            TreeMap<LocalDate, TreeSet<TrainingDTO>> allTrainings = trainingService.getAllTrainings(userDTO.getEmail());
            int totalTrainings = 0;
            for (TreeSet<TrainingDTO> trainingsOnDate : allTrainings.values()) {
                totalTrainings += trainingsOnDate.size();
            }
            return totalTrainings;
        } else {
            throw new NoStatisticsRightsException();
        }

    }

    /**
     * Получает общую продолжительность тренировок пользователя за определенный период.
     *
     * @param userDTO   пользователь, для которого нужно получить статистику
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая продолжительность тренировок за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        if (hisRight(user.getRights(), "STATISTICS")) {
            int totalTrainings;
            List<TrainingDTO> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
            totalTrainings = trainings.size();
            return totalTrainings;
        } else {
            throw new NoStatisticsRightsException();
        }
    }

    /**
     * Получает общее количество сожженных калорий пользователем за определенный период.
     *
     * @param userDTO   пользователь, для которого нужно получить статистику
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общее количество сожженных калорий за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public Integer getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        if (hisRight(user.getRights(), "STATISTICS")) {
            int totalDuration = 0;
            List<TrainingDTO> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
            for (TrainingDTO trainingDTO : trainings) {
                totalDuration += trainingDTO.getDuration();
            }
            return totalDuration;
        } else {
            throw new NoStatisticsRightsException();
        }
    }

    @Override
    public Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
        if (hisRight(user.getRights(), "STATISTICS")) {
            int totalCaloriesBurned = 0;
            List<TrainingDTO> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
            for (TrainingDTO trainingDTO : trainings) {
                totalCaloriesBurned += trainingDTO.getCaloriesBurned();
            }
            return totalCaloriesBurned;
        } else {
            throw new NoStatisticsRightsException();
        }
    }

    private List<TrainingDTO> getTrainingsInPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTrainings = trainingService.getAllTrainings(userDTO.getEmail());
        List<TrainingDTO> trainingsInPeriod = new ArrayList<>();
        for (TreeSet<TrainingDTO> trainingsOnDate : allTrainings.values()) {
            for (TrainingDTO trainingDTO : trainingsOnDate) {
                if (isTrainingInPeriod(trainingDTO, startDate, endDate)) {
                    trainingsInPeriod.add(trainingDTO);
                }
            }
        }
        return trainingsInPeriod;
    }

    private boolean isTrainingInPeriod(TrainingDTO trainingDTO, LocalDate startDate, LocalDate endDate) {
        LocalDate trainingDate = trainingDTO.getDate();
        return !trainingDate.isBefore(startDate) && !trainingDate.isAfter(endDate);
    }
}
