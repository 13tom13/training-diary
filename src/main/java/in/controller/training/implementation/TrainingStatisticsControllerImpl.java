package in.controller.training.implementation;

import in.controller.training.TrainingStatisticsController;
import exceptions.security.rights.NoStatisticsRightsException;
import utils.Logger;
import model.User;
import in.service.training.TrainingStatisticsService;

/**
 * Реализация контроллера статистики тренировок.
 */
public class TrainingStatisticsControllerImpl implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    private static Logger logger;

    /**
     * Конструктор контроллера статистики тренировок.
     * @param trainingStatisticsService Сервис статистики тренировок
     */
    public TrainingStatisticsControllerImpl(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
        logger = Logger.getInstance();
    }

    /**
     * Получает статистику всех тренировок пользователя.
     * @param user Пользователь
     * @return Статистика всех тренировок
     */
    public int getAllTrainingStatistics(User user) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatistics(user);
            logger.logAction(user.getEmail(), "get all training statistics");
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику всех тренировок пользователя за определенный период.
     * @param user Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика всех тренировок за указанный период
     */
    public Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get all training statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику продолжительности тренировок пользователя за определенный период.
     * @param user Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика продолжительности тренировок за указанный период
     */
    public int getDurationStatisticsPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getDurationStatisticsPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get duration statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику сжигания калорий пользователя за определенный период.
     * @param user Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика сжигания калорий за указанный период
     * @throws RuntimeException если возникает ошибка доступа к статистике
     */
    public int getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) {
        try {
            int result = trainingStatisticsService.getCaloriesBurnedPerPeriod(user, startDate, endDate);
            logger.logAction(user.getEmail(),
                    "get calories burned statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            throw new RuntimeException(e);
        }
    }
}