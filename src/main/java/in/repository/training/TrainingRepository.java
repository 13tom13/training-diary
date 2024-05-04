package in.repository.training;

import exceptions.RepositoryException;
import entities.model.Training;
import entities.model.User;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Интерфейс для работы с хранилищем тренировок.
 */
public interface TrainingRepository {

    /**
     * Получить все тренировки для указанного пользователя, сгруппированные по дате.
     *
     * @param user электронная почта пользователя
     * @return отображение даты на множество тренировок
     */
    TreeMap<LocalDate, TreeSet<Training>> getAllTrainingsByUserEmail(User user);

    /**
     * Получить тренировки для указанного пользователя на указанную дату.
     *
     * @param user         электронная почта пользователя
     * @param trainingDate дата тренировки
     * @return множество тренировок на указанную дату
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    TreeSet<Training> getTrainingsByUserEmailAndData(User user, LocalDate trainingDate) throws RepositoryException;

    /**
     * Получить тренировку по электронной почте пользователя, дате и названию.
     *
     * @param user         электронная почта пользователя
     * @param trainingDate дата тренировки
     * @param trainingName название тренировки
     * @return тренировка
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    Training getTrainingByUserEmailAndDataAndName(User user, LocalDate trainingDate, String trainingName) throws RepositoryException;

    /**
     * Сохранить новую тренировку для указанного пользователя.
     *
     * @param user        пользователь
     * @param newTraining новая тренировка
     * @return Сохраненная тренировка
     * @throws RepositoryException если тренировка уже существует или возникла ошибка при доступе к хранилищу
     */
    Training saveTraining(User user, Training newTraining) throws RepositoryException;

    /**
     * Удалить указанную тренировку для указанного пользователя.
     *
     * @param user     электронная почта пользователя
     * @param training тренировка для удаления
     * @throws RepositoryException если тренировка для удаления не найдена или возникла ошибка при доступе к хранилищу
     */
    void deleteTraining(User user, Training training) throws RepositoryException;

    /**
     * Обновить существующую тренировку для указанного пользователя.
     *
     * @param user        электронная почта пользователя
     * @param oldTraining старая версия тренировки
     * @param newTraining новая версия тренировки
     * @return измененная тренировка
     * @throws RepositoryException если тренировка для обновления не найдена или возникла ошибка при доступе к хранилищу
     */
    Training updateTraining(User user, Training oldTraining, Training newTraining) throws RepositoryException;
}

