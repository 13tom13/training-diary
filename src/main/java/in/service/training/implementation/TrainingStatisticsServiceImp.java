package in.service.training.implementation;

import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingStatisticsService;
import model.Rights;
import model.Training;
import model.User;
import in.service.training.TrainingService;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

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
     * @param user пользователь, для которого нужно получить статистику
     * @return общее количество тренировок
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public int getAllTrainingStatistics(User user) throws NoStatisticsRightsException {
        if (!user.getRights().contains(Rights.STATISTICS)) {
            throw new NoStatisticsRightsException();
        }
        TreeMap<String, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(user);
        int totalTrainings = 0;
        for (TreeSet<Training> trainingsOnDate : allTrainings.values()) {
            totalTrainings += trainingsOnDate.size();
        }
        return totalTrainings;
    }

    /**
     * Получает общую продолжительность тренировок пользователя за определенный период.
     *
     * @param user      пользователь, для которого нужно получить статистику
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая продолжительность тренировок за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException {
        if (!user.getRights().contains(Rights.STATISTICS)) {
            throw new NoStatisticsRightsException();
        }
        int totalTrainings;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        totalTrainings = trainings.size();

        return totalTrainings;
    }

    /**
     * Получает общее количество сожженных калорий пользователем за определенный период.
     *
     * @param user      пользователь, для которого нужно получить статистику
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общее количество сожженных калорий за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    @Override
    public Integer getDurationStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException {
        if (!user.getRights().contains(Rights.STATISTICS)) {
            throw new NoStatisticsRightsException();
        }
        int totalDuration = 0;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        for (Training training : trainings) {
            totalDuration += training.getDuration();
        }
        return totalDuration;
    }

    @Override
    public Integer getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException {
        if (!user.getRights().contains(Rights.STATISTICS)) {
            throw new NoStatisticsRightsException();
        }
        int totalCaloriesBurned = 0;
        List<Training> trainings = getTrainingsInPeriod(user, startDate, endDate);
        for (Training training : trainings) {
            totalCaloriesBurned += training.getCaloriesBurned();
        }
        return totalCaloriesBurned;
    }

    private List<Training> getTrainingsInPeriod(User user, String startDate, String endDate) {
        TreeMap<String, TreeSet<Training>> allTrainings = trainingService.getAllTrainings(user);
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

    private boolean isTrainingInPeriod(Training training, String startDate, String endDate) {
        String trainingDate = training.getDate();
        return trainingDate.compareTo(startDate) >= 0 && trainingDate.compareTo(endDate) <= 0;
    }
}

