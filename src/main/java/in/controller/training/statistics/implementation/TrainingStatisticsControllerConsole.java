package in.controller.training.statistics.implementation;

import entities.dto.UserDTO;
import in.controller.training.statistics.TrainingStatisticsController;
import exceptions.security.rights.NoStatisticsRightsException;
import utils.Logger;
import in.service.training.TrainingStatisticsService;

import java.time.LocalDate;


/**
 * Реализация контроллера статистики тренировок.
 */
public class TrainingStatisticsControllerConsole implements TrainingStatisticsController {

    private final TrainingStatisticsService trainingStatisticsService;

    private static Logger logger;

    /**
     * Конструктор контроллера статистики тренировок.
     */
    public TrainingStatisticsControllerConsole(TrainingStatisticsService trainingStatisticsService) {
        this.trainingStatisticsService = trainingStatisticsService;
        logger = Logger.getInstance();
    }

    /**
     * Получает статистику всех тренировок пользователя.
     * @param userDTO Пользователь
     * @return Статистика всех тренировок
     */
    public Integer getAllTrainingStatistics(UserDTO userDTO) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatistics(userDTO);
            logger.logAction(userDTO.getEmail(), "get all training statistics");
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику всех тренировок пользователя за определенный период.
     * @param userDTO Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика всех тренировок за указанный период
     */
    public Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(userDTO, startDate, endDate);
            logger.logAction(userDTO.getEmail(),
                    "get all training statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику продолжительности тренировок пользователя за определенный период.
     * @param userDTO Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика продолжительности тренировок за указанный период
     */
    public Integer getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getDurationStatisticsPerPeriod(userDTO, startDate, endDate);
            logger.logAction(userDTO.getEmail(),
                    "get duration statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Получает статистику сжигания калорий пользователя за определенный период.
     * @param userDTO Пользователь
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Статистика сжигания калорий за указанный период
     * @throws RuntimeException если возникает ошибка доступа к статистике
     */
    public Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getCaloriesBurnedPerPeriod(userDTO, startDate, endDate);
            logger.logAction(userDTO.getEmail(),
                    "get calories burned statistics per period " + startDate + " - " + endDate);
            return result;
        } catch (NoStatisticsRightsException e) {
            throw new RuntimeException(e);
        }
    }
}