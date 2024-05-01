package in.controller.training;

import entities.dto.UserDTO;

import java.util.Date;

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
    int getAllTrainingStatistics(UserDTO userDTO);

    /**
     * Получает общую статистику по всем тренировкам пользователя за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая статистика по всем тренировкам пользователя за указанный период
     */
    Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, Date startDate, Date endDate);

    /**
     * Получает статистику по длительности тренировок пользователя за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по длительности тренировок пользователя за указанный период
     */
    int getDurationStatisticsPerPeriod(UserDTO userDTO, Date startDate, Date endDate);

    /**
     * Получает статистику по сожженным калориям пользователем за определенный период.
     *
     * @param userDTO      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по сожженным калориям пользователем за указанный период
     */
    int getCaloriesBurnedPerPeriod(UserDTO userDTO, Date startDate, Date endDate);
}
