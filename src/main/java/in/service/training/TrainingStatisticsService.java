package in.service.training;

import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;

import java.time.LocalDate;


/**
 * Интерфейс для сервиса статистики тренировок.
 */
public interface TrainingStatisticsService {

    /**
     * Получает общую статистику по тренировкам для указанного пользователя.
     *
     * @param userDTO пользователь, для которого запрашивается статистика
     * @return общая статистика по тренировкам
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getAllTrainingStatistics(UserDTO userDTO) throws NoStatisticsRightsException;

    /**
     * Получает общую статистику по тренировкам за указанный период для указанного пользователя.
     *
     * @param userDTO   пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return общая статистика по тренировкам за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException;

    /**
     * Получает статистику по продолжительности тренировок за указанный период для указанного пользователя.
     *
     * @param userDTO   пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по продолжительности тренировок за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException;

    /**
     * Получает статистику по сожженным калориям за указанный период для указанного пользователя.
     *
     * @param userDTO   пользователь, для которого запрашивается статистика
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return статистика по сожженным калориям за указанный период
     * @throws NoStatisticsRightsException если у пользователя нет прав на просмотр статистики
     */
    Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException;
}