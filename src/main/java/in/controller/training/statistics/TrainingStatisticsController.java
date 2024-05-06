package in.controller.training.statistics;

import entities.dto.UserDTO;

import java.time.LocalDate;


/**
 * Интерфейс контроллера статистики тренировок.
 */
public interface TrainingStatisticsController {

    /**
     * Получает общую статистику по всем тренировкам пользователя.
     *
     * @param userDTO пользователь, для которого запрашивается статистика
     * @return общая статистика по всем тренировкам пользователя
     */
    Integer getAllTrainingStatistics(UserDTO userDTO);

    /**
     * Получает общую статистику по всем тренировкам пользователя за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая статистика по всем тренировкам пользователя за указанный период
     */
    Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate);

    /**
     * Получает статистику по длительности тренировок пользователя за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по длительности тренировок пользователя за указанный период
     */
    Integer getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate);

    /**
     * Получает статистику по сожженным калориям пользователем за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по сожженным калориям пользователем за указанный период
     */
    Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate);
}