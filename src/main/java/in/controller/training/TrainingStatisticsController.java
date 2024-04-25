package in.controller.training;

import model.User;

import java.util.Date;

/**
 * Интерфейс контроллера статистики тренировок.
 */
public interface TrainingStatisticsController {

    /**
     * Получает общую статистику по всем тренировкам пользователя.
     *
     * @param user пользователь, для которого запрашивается статистика
     * @return общая статистика по всем тренировкам пользователя
     */
    int getAllTrainingStatistics(User user);

    /**
     * Получает общую статистику по всем тренировкам пользователя за определенный период.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая статистика по всем тренировкам пользователя за указанный период
     */
    Integer getAllTrainingStatisticsPerPeriod(User user, Date startDate, Date endDate);

    /**
     * Получает статистику по длительности тренировок пользователя за определенный период.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по длительности тренировок пользователя за указанный период
     */
    int getDurationStatisticsPerPeriod(User user, Date startDate, Date endDate);

    /**
     * Получает статистику по сожженным калориям пользователем за определенный период.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по сожженным калориям пользователем за указанный период
     */
    int getCaloriesBurnedPerPeriod(User user, Date startDate, Date endDate);
}
