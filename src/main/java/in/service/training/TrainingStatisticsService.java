package in.service.training;

import exceptions.security.rights.NoStatisticsRightsException;
import model.User;

/**
 * Интерфейс для сервиса статистики тренировок.
 */
public interface TrainingStatisticsService {

    /**
     * Получает общую статистику по тренировкам для указанного пользователя.
     *
     * @param user пользователь, для которого запрашивается статистика
     * @return общая статистика по тренировкам
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    int getAllTrainingStatistics(User user) throws NoStatisticsRightsException;

    /**
     * Получает общую статистику по тренировкам за указанный период для указанного пользователя.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая статистика по тренировкам за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getAllTrainingStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;

    /**
     * Получает статистику по продолжительности тренировок за указанный период для указанного пользователя.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по продолжительности тренировок за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getDurationStatisticsPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;

    /**
     * Получает статистику по сожженным калориям за указанный период для указанного пользователя.
     *
     * @param user      пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по сожженным калориям за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getCaloriesBurnedPerPeriod(User user, String startDate, String endDate) throws NoStatisticsRightsException;
}
