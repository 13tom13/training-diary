package in.service.training.implementation;

import entities.dto.UserDTO;
import entities.model.Training;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingService;
import in.service.training.TrainingStatisticsService;

import java.util.*;

import static utils.Utils.hisRight;

/**
 * Реализация сервиса статистики тренировок.
 */
public class TrainingStatisticsServiceImp implements TrainingStatisticsService {

    private final TrainingService trainingService;

    /**
     * Создает новый экземпляр TrainingStatisticsServiceImpl.
     *
     * @param trainingService сервис тренировок, используемый для получения информации о тренировках
     */
    public TrainingStatisticsServiceImp(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    /**
     * Получает общее количество тренировок пользователя.
     *
     * @param userDTO пользователь, для которого нужно получить статистику
     * @return общее количество тренировок
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public int getAllTrainingStatistics(UserDTO userDTO) throws NoStatisticsRightsException {
        if (hisRight(userDTO, "STATISTICS")) {
            TreeMap<Date, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(userDTO);
            int totalTrainings = 0;
            for (TreeSet<Training> trainingsOnDate : allTrainings.values()) {
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
    public Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, Date startDate, Date endDate) throws NoStatisticsRightsException {
        if (hisRight(userDTO, "STATISTICS")) {
            int totalTrainings;
            List<Training> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
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
    public Integer getDurationStatisticsPerPeriod(UserDTO userDTO, Date startDate, Date endDate) throws NoStatisticsRightsException {
        if (hisRight(userDTO, "STATISTICS")) {
            int totalDuration = 0;
            List<Training> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
            for (Training training : trainings) {
                totalDuration += training.getDuration();
            }
            return totalDuration;
        } else {
            throw new NoStatisticsRightsException();
        }
    }

    @Override
    public Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, Date startDate, Date endDate) throws NoStatisticsRightsException {
        if (hisRight(userDTO, "STATISTICS")) {
            int totalCaloriesBurned = 0;
            List<Training> trainings = getTrainingsInPeriod(userDTO, startDate, endDate);
            for (Training training : trainings) {
                totalCaloriesBurned += training.getCaloriesBurned();
            }
            return totalCaloriesBurned;
        } else {
            throw new NoStatisticsRightsException();
        }
    }

    private List<Training> getTrainingsInPeriod(UserDTO userDTO, Date startDate, Date endDate) {
        TreeMap<Date, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(userDTO);
        List<Training> trainingsInPeriod = new ArrayList<>();
        for (TreeSet<Training> trainingsOnDate : allTrainings.values()) {
            for (Training training : trainingsOnDate) {
                if (isTrainingInPeriod(training, startDate, endDate)) {
                    trainingsInPeriod.add(training);
                }
            }
        }
        return trainingsInPeriod;
    }

    private boolean isTrainingInPeriod(Training training, Date startDate, Date endDate) {
        Date trainingDate = training.getDate();
        return trainingDate.compareTo(startDate) >= 0 && trainingDate.compareTo(endDate) <= 0;
    }
}

